--------------------------------------------------------------------------------
-- Company: 
-- Engineer: Sebastián Gómez
--
-- Create Date:   16:45:10 05/03/2010
-- Design Name:   
-- Module Name:   /home/seb/Documentos/utp/octavo/lab_micro/DAC/dac2/DAC_test_tb.vhd
-- Project Name:  sga
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: DAC_test
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
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;
 
ENTITY DAC_test_tb IS
END DAC_test_tb;
 
ARCHITECTURE behavior OF DAC_test_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT DAC_test
    PORT(
         spi_sck : OUT  std_logic;
         spi_miso : IN  std_logic;
         spi_mosi : OUT  std_logic;
         spi_dac_cs : OUT  std_logic;
         spi_rom_cs : OUT  std_logic;
         strataflash_ce : OUT  std_logic;
         strataflash_oe : OUT  std_logic;
         platformflash_oe : OUT  std_logic;
         spi_amp_cs : OUT  std_logic;
         strataflash_we : OUT  std_logic;
         spi_adc_conv : OUT  std_logic;
         leds_out : OUT  std_logic_vector(7 downto 0);
         clk : IN  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal spi_miso : std_logic := '0';
   signal clk : std_logic := '0';

 	--Outputs
   signal spi_sck : std_logic;
   signal spi_mosi : std_logic;
   signal spi_dac_cs : std_logic;
   signal spi_rom_cs : std_logic;
   signal strataflash_ce : std_logic;
   signal strataflash_oe : std_logic;
   signal platformflash_oe : std_logic;
   signal spi_amp_cs : std_logic;
   signal strataflash_we : std_logic;
   signal spi_adc_conv : std_logic;
   signal leds_out : std_logic_vector(7 downto 0);

   -- Clock period definitions
   constant clk_period : time := 1us;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: DAC_test PORT MAP (
          spi_sck => spi_sck,
          spi_miso => spi_miso,
          spi_mosi => spi_mosi,
          spi_dac_cs => spi_dac_cs,
          spi_rom_cs => spi_rom_cs,
          strataflash_ce => strataflash_ce,
          strataflash_oe => strataflash_oe,
          platformflash_oe => platformflash_oe,
          spi_amp_cs => spi_amp_cs,
          strataflash_we => strataflash_we,
          spi_adc_conv => spi_adc_conv,
          leds_out => leds_out,
          clk => clk
        );

   -- Clock process definitions
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
      wait for 100ns;	

      wait for clk_period*10;

      -- insert stimulus here 

      wait;
   end process;

END;
