--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   20:13:13 05/28/2010
-- Design Name:   
-- Module Name:   /sv/tb_test_spi_ctrl.vhd
-- Project Name:  sv
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: TEST_SPI_CTRL
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY tb_test_spi_ctrl IS
END tb_test_spi_ctrl;
 
ARCHITECTURE behavior OF tb_test_spi_ctrl IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT TEST_SPI_CTRL
    PORT(
         spi_miso : IN  std_logic;
         spi_mosi : OUT  std_logic;
         spi_clk : OUT  std_logic;
         spi_dac_cs : OUT  std_logic;
         spi_rom_cs : OUT  std_logic;
         strataflash_ce : OUT  std_logic;
         strataflash_oe : OUT  std_logic;
         platformflash_oe : OUT  std_logic;
         spi_amp_cs : OUT  std_logic;
         strataflash_we : OUT  std_logic;
         spi_adc_conv : OUT  std_logic;
         dac_clr : OUT  std_logic;
         clk : IN  std_logic;
         switches : IN  std_logic_vector(7 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal spi_miso : std_logic := '0';
   signal clk : std_logic := '0';
   signal switches : std_logic_vector(7 downto 0) := (others => '0');

 	--Outputs
   signal spi_mosi : std_logic;
   signal spi_clk : std_logic;
   signal spi_dac_cs : std_logic;
   signal spi_rom_cs : std_logic;
   signal strataflash_ce : std_logic;
   signal strataflash_oe : std_logic;
   signal platformflash_oe : std_logic;
   signal spi_amp_cs : std_logic;
   signal strataflash_we : std_logic;
   signal spi_adc_conv : std_logic;
   signal dac_clr : std_logic;

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: TEST_SPI_CTRL PORT MAP (
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
          clk => clk,
          switches => switches
        );
 
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
      -- hold reset state for 100 ns.
      wait for 100 ns;	

      -- insert stimulus here 

      wait;
   end process;

END;
