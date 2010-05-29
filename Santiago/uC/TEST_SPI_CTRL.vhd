library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity TEST_SPI_CTRL is

	Port (
           spi_miso : in  STD_LOGIC;
           spi_mosi : out  STD_LOGIC;
           spi_clk : out STD_LOGIC;
           spi_dac_cs,spi_rom_cs,strataflash_ce,
	   strataflash_oe,platformflash_oe,spi_amp_cs, strataflash_we,
           spi_adc_conv : out STD_LOGIC;
	   dac_clr : out STD_LOGIC;
	   clk : in STD_LOGIC;
	   switches : in STD_LOGIC_VECTOR (7 downto 0)
           );

end TEST_SPI_CTRL;

architecture arq_test_spi_ctrl of TEST_SPI_CTRL is

	component SPI_CTRL is
	
	Port ( 
		dataIn : in STD_LOGIC_VECTOR (31 downto 0);
		dataOut : out STD_LOGIC_VECTOR (31 downto 0);
           	configIn : in STD_LOGIC_VECTOR (7 downto 0);
           	spi_miso : in STD_LOGIC;
           	spi_mosi : out STD_LOGIC;
		spi_clk : out STD_LOGIC;
		spi_dac_cs,spi_rom_cs,strataflash_ce,
		strataflash_oe,platformflash_oe,spi_amp_cs, strataflash_we,
		spi_adc_conv : out STD_LOGIC;
		dac_clr : out STD_LOGIC;
		ready : out STD_LOGIC;
		clk : in STD_LOGIC
           );

	end component;

	signal dataIn, dataOut : STD_LOGIC_VECTOR (31 downto 0) := (others => '0');
	signal configIn : STD_LOGIC_VECTOR (7 downto 0) := (others => '0');
	signal ready : STD_LOGIC := '0';
	signal estado : integer range 0 to 10;
	signal lectura, aumento : STD_LOGIC_VECTOR (11 downto 0);
begin
	SPI_CTRL_0 : SPI_CTRL 
        port map (
	  dataIn => dataIn,
	  dataOut => dataOut,
	  configIn => configIn,
	  spi_miso => spi_miso,
	  spi_mosi => spi_mosi,
	  spi_clk => spi_clk,
	  spi_dac_cs => spi_dac_cs,
          spi_rom_cs => spi_rom_cs,
	  strataflash_ce => strataflash_ce,
	  strataflash_oe => strataflash_oe,
          platformflash_oe => platformflash_oe,
          spi_amp_cs => spi_amp_cs, 
          strataflash_we => strataflash_we,
	  spi_adc_conv => spi_adc_conv,
	  dac_clr => dac_clr,
	  ready => ready,
	  clk => clk
	);

	aumento <= switches & "0000";

	process(clk)
	begin
		if rising_edge(clk) then
			estado <= estado + 1;
			if estado = 0 then
				configIn <= "00010001"; -- Amplificador
				dataIn <= "00000000000000000000000000010001"; -- Configuracion de ganancia
			elsif estado = 1 then
				if ready = '0' then
					estado <= 1;
				end if;
			elsif estado = 2 then
				configIn <= "00010011"; -- ADC
			elsif estado = 3 then
				if ready = '0' then
					estado <= 3;
				else
					lectura <= dataOut (11 downto 0) + aumento;
				end if;
			elsif estado = 4 then
				configIn <= "00010011"; -- DAC
				dataIn <= "00000000" & "0011" & "0000" & lectura & "0000";
			elsif estado = 5 then
				if ready = '0' then
					estado <= 5;
				else
					estado <= 0;
				end if;
			end if;
		end if;
	end process;
	
	
end arq_test_spi_ctrl;
				
