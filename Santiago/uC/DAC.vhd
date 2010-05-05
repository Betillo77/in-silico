----------------------------------------------------------------------------------
-- Company: 
-- Engineer: Sebastián Gómez
-- 
-- Create Date:    14:15:10 04/26/2010 
-- Design Name: 
-- Module Name:    DAC - DAC_Arq 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity DAC is
    Port ( data : in  STD_LOGIC_VECTOR (11 downto 0);
           spi_sck : in  STD_LOGIC;
           da_conv : in  STD_LOGIC;
           addr : in  STD_LOGIC_VECTOR (3 downto 0);
           spi_miso : in  STD_LOGIC;
           ready : out  STD_LOGIC;
           spi_mosi : out  STD_LOGIC;
           dac_cs : out  STD_LOGIC);
end DAC;

architecture DAC_Arq of DAC is
	signal sready : std_logic := '1';
	signal sdata : std_logic_vector(31 downto 0) := x"00000000";
	signal estado : std_logic_vector(5 downto 0) := "000000";
	
	signal command : std_logic_vector(3 downto 0);
	signal currBit : std_logic_vector(4 downto 0);
begin
	-- constantes
	command <= "0011";
	
	--señales
	dac_cs <= sready;
	ready <= sready;
	currBit <= estado(4 downto 0); -- byte actual: 4 bits menos sig de estado
	
	process (spi_sck)
	begin
		
		if (falling_edge(spi_sck)) then
			--sready: indica si el conversor está listo para recibir un dato nuevo
			if (sready='1' and da_conv='1') then
				sready <= '0';
				sdata <= "00000000" & command & addr & data & "0000";
				estado <= "111110";
				spi_mosi <= '0';
			end if;
			
			-- envío de datos
			if (sready='0') then
				spi_mosi <= sdata(conv_integer(currBit));
				if (estado/="011111") then					
					estado <= estado - 1;
				else
					estado <= "000000";
					sready <= '1';
				end if;	
			end if;
		end if;
	end process;

end DAC_Arq;

