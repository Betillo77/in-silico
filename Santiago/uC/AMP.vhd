library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity AMP is

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

architecture arq_AMP of AMP is

   signal state : integer range 0 to 9 := 0;
   signal counter_sck : integer range 0 to 3 :=0;
   signal clk_spi : std_logic:='0';
   signal ready_s : std_logic := '0';
	signal enable : STD_LOGIC;
	signal gain_amp : std_logic_vector(7 downto 0);
	
begin

	ready <= ready_s;
	spi_clk <= clk_spi;
	spi_cs <= enable;

	process (clk, configIn)
	begin
		if (configIn(3 downto 0) /= "0001" or configIn(4) = '0') then
			enable <= '1';
			state <= 0;
			spi_mosi <= '0';
			ready_s <= '1';
		elsif rising_edge(clk) then
			if clk_spi = '1' and counter_sck = 2 then
				state <= state + 1;
				case state is
					when 0 => 
						enable <= '0';
						gain_amp <= dataIn(7 downto 0);
						ready_s <= '0';
					when 1 to 8 =>
						spi_mosi <= gain_amp(8 - state);
					when others =>
						enable <= '1';
						state <= 9;
						spi_mosi <= '0';
						ready_s <= '1';
				end case;
			end if;
		end if;
	end process;
	
	process (clk, configIn)
	begin
		if (configIn(3 downto 0) /= "0001" or configIn(4) = '0') then
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
	
end arq_AMP;
