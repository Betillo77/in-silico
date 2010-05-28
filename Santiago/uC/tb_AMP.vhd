LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;
 
ENTITY tb_amp2 IS
END tb_amp2;
 
ARCHITECTURE behavior OF tb_AMP IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT AMP
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
	
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: AMP PORT MAP (
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

   clk_process :process
   begin
		clk <= '0';
		wait for 5 ns;
		clk <= '1';
		wait for 5 ns;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100ms.
      wait for 100 ns;	
		configIn <= "00010001";
		dataIn <= "00000000000000000000000000010001";

      -- insert stimulus here 

      wait;
   end process;

END;
