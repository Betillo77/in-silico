
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SPI_CTRL is
	Port ( dataIn : in  STD_LOGIC_VECTOR (31 downto 0);
           dataOut : out  STD_LOGIC_VECTOR (31 downto 0);
           configIn : in  STD_LOGIC_VECTOR(7 downto 0);
           spi_miso : in  STD_LOGIC;
           spi_mosi : out  STD_LOGIC;
			  spi_clk : out STD_LOGIC;
			  spi_dac_cs,spi_rom_cs,strataflash_ce,
			  strataflash_oe,platformflash_oe,spi_amp_cs, strataflash_we,
			  spi_adc_conv : out STD_LOGIC;
			  dac_clr : out STD_LOGIC;
			  ready : out  STD_LOGIC;
			  clk : in STD_LOGIC
           );
end SPI_CTRL;

architecture spi_beh of SPI_CTRL is

	component DAC is
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
	end component;

	signal sclk : std_logic;
	signal command : std_logic_vector(3 downto 0);

	signal dac_mosi : std_logic;
	signal dac_clk : std_logic;
	signal dac_ready : std_logic;
begin
	-- No definidas aun
	spi_rom_cs <= '1';	
	strataflash_ce <= '1';
	strataflash_we <= '1';
	strataflash_oe <= '1';
	platformflash_oe <= '0';
	spi_amp_cs <= '1';
	spi_adc_conv <= '1';
	dac_clr <= '1';
	
	--asignación estática de variables
	dac_clr <= '1';
	
	sclk <= clk;
	command <= configIn(3 downto 0);
	
	DAC0 : DAC port map (
	  dataIn => dataIn,
	  dataOut => dataOut,
	  configIn => configIn,
	  spi_miso => spi_miso,
	  spi_mosi => dac_mosi,
	  spi_clk => dac_clk,
	  spi_cs => spi_dac_cs,
	  ready => dac_ready,
	  clk => sclk
	);
	
	--multiplexación SPI
	spi_mosi <= dac_mosi when command="0011" else
		'0';
	
	spi_clk <= dac_clk when command="0011" else
		'0';
		
	ready <= dac_ready; --usar ANDs

end spi_beh;

