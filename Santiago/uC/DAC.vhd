
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity DAC is
    Port ( dataIn : in  STD_LOGIC_VECTOR (31 downto 0);
           dataOut : out  STD_LOGIC_VECTOR (31 downto 0);
           configIn : in  STD_LOGIC_VECTOR(7 downto 0);
           spi_miso : in  STD_LOGIC;
           spi_mosi : out  STD_LOGIC;
			  spi_clk : out STD_LOGIC;
			  spi_cs : out STD_LOGIC;
			  ready : out  STD_LOGIC;
			  clk : in STD_LOGIC
           );
end DAC;

architecture DAC_Arq of DAC is
	signal sready : std_logic := '1';
	signal sdata, sout : std_logic_vector(31 downto 0) := x"00000000";
	signal estado : std_logic_vector(5 downto 0) := "000000";
	
	constant frec_div : std_logic_vector(3 downto 0) := "0101"; --const 10MHz
	signal frec_cnt : std_logic_vector(3 downto 0) :="0000";
	signal spi_sck : std_logic:='0';
	signal currBit : std_logic_vector(4 downto 0):="00000";
	
	signal command : std_logic_vector(3 downto 0);
	signal da_conv : std_logic;
	signal smosi : std_logic := '0';
begin
	
	--señales
	spi_cs <= sready;
	ready <= sready;
	currBit <= estado(4 downto 0); -- byte actual: 4 bits menos sig de estado
	spi_clk <= spi_sck;
	sdata <= dataIn;
	dataOut <= sout;
	spi_mosi <= smosi;
		
	command <= configIn(3 downto 0);
	da_conv <= configIn(4);
	
	process (clk)
	begin
		if rising_edge(clk) then
			if frec_cnt=frec_div then
				frec_cnt<="0001";
				spi_sck <= not spi_sck;
			else
				frec_cnt <= frec_cnt+1;
			end if;
		end if;
	end process;
	
	
	process (spi_sck)
	begin
		
		if (falling_edge(spi_sck)) then
			--sready: indica si el conversor está listo para recibir un dato nuevo
			if (sready='1' and da_conv='1' and command="0011" and estado="000000") then
				sready <= '0';
				estado <= "111110";
				smosi <= '0';
			end if;
			
			-- envío de datos
			if (sready='0') then
				smosi <= sdata(conv_integer(currBit));
				if (estado/="011111") then					
					estado <= estado - 1;
				else
					--estado <= "000000";
					sready <= '1';
				end if;	
			end if;
			
			if (da_conv='0' and estado="011111") then
				estado<="000000";
			end if;
		end if;
	end process;

end DAC_Arq;


