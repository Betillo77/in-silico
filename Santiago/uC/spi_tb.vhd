LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;
 
ENTITY dac2_tb IS
END dac2_tb;
 
ARCHITECTURE behavior OF dac2_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT DAC
    PORT(
         dataIn : IN  std_logic_vector(31 downto 0);
         dataOut : OUT  std_logic_vector(31 downto 0);
         configIn : IN  std_logic_vector(7 downto 0);
         spi_miso : IN  std_logic;
         spi_mosi : OUT  std_logic;
         spi_clk : OUT  std_logic;
         spi_cs : OUT  std_logic;
         ready : OUT  std_logic;
         clk : IN  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal dataIn : std_logic_vector(31 downto 0) := (others => '0');
   signal configIn : std_logic_vector(7 downto 0) := (others => '0');
   signal spi_miso : std_logic := '0';
   signal clk : std_logic := '0';

 	--Outputs
   signal dataOut : std_logic_vector(31 downto 0);
   signal spi_mosi : std_logic;
   signal spi_clk : std_logic;
   signal spi_cs : std_logic;
   signal ready : std_logic;

   -- Clock period definitions
   constant spi_clk_period : time := 1us;
   constant clk_period : time := 1us;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: DAC PORT MAP (
          dataIn => dataIn,
          dataOut => dataOut,
          configIn => configIn,
          spi_miso => spi_miso,
          spi_mosi => spi_mosi,
          spi_clk => spi_clk,
          spi_cs => spi_cs,
          ready => ready,
          clk => clk
        );

   -- Clock process definitions
   spi_clk_process :process
   begin
		spi_clk <= '0';
		wait for spi_clk_period/2;
		spi_clk <= '1';
		wait for spi_clk_period/2;
   end process;
 
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100ms.
      wait for 10us;
		
		dataIn <= x"00300f00";
		configIn <= x"03";
		wait for 10us;
		configIn <= x"13";

      wait for spi_clk_period*10;

      -- insert stimulus here 

      wait;
   end process;

END;

