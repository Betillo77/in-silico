library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity ADC is

  	Port (dataIn : in  STD_LOGIC_VECTOR (31 downto 0);
         dataOut : out  STD_LOGIC_VECTOR (31 downto 0);
         configIn : in  STD_LOGIC_VECTOR(7 downto 0);
         spi_miso : in  STD_LOGIC;
         spi_mosi : out  STD_LOGIC;
		   spi_clk : out STD_LOGIC;
		   spi_cs : out STD_LOGIC;
		   ready : out  STD_LOGIC;
			clk : in STD_LOGIC
    );
	 
end entity;

architecture arq_ADC of ADC is

    signal state		      : integer range 0 to 35 := 0;-- los estados maquina
    signal salida     	   : std_logic_vector(31 downto 0); -- salida ADC
    signal counter_sck	   : integer range 0 to 3 :=0;--contador
    signal clk_spi		   : std_logic:='0'; --reloj spi
    signal ready_s	   : std_logic:='0';--actualiza el dato convertido
	 signal enable : STD_LOGIC;

begin   

   ready <= ready_s;
   dataOut <= salida;
   spi_clk <= clk_spi;
	spi_cs <= enable;

	process (clk)
	begin
		if (configIn(3 downto 0) /= "0011" or configIn(4) = '0') then
			state <= 0;
			enable <= '0';
			salida <= (others => '1');
			ready_s <= '1';
		elsif rising_edge(clk) then
			if clk_spi	= '1' and counter_sck = 2 then
				state <= state + 1;
				case state is
					when 0 => 
						enable <= '1';
						ready <= '0';
					when 1 =>
						salida <= (others=>'1');
						enable <= '0';
					when 3 =>
						if spi_miso = '1' then
							salida <= (others=>'1');
						else
							salida <= (others=>'1');
						end if;
					when 4 to 16 =>
						salida <= salida(30 downto 0) & spi_miso;
					when 20 to 31 =>
						salida <= salida(30 downto 0) & spi_miso;
					when 32 =>
						salida <= salida(30 downto 0) & spi_miso;
						ready_s <= '1';
					when 34 =>
						state <= 34;
					when others =>
				end case;
			end if;
		end if;
	end process;
       
	process (clk)
	begin
		if (configIn(3 downto 0) /= "0011" or configIn(4) = '0') then
			counter_sck <= 0;
			clk_spi <= '0';
		elsif rising_edge(clk) then
			counter_sck <= counter_sck + 1;
			if counter_sck >= 2 then
				counter_sck <= 0;
				clk_spi <= not clk_spi;
			end if;
		end if;
	end process;
	
end arq_ADC;
