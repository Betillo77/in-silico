--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   12:48:48 05/03/2010
-- Design Name:   
-- Module Name:   /home/seb/Documentos/utp/octavo/lab_micro/doc/EL ADC BUENO/dac_tb.vhd
-- Project Name:  adc
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: DAC
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
 
ENTITY dac_tb IS
END dac_tb;
 
ARCHITECTURE behavior OF dac_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT DAC
    PORT(
         data : IN  std_logic_vector(11 downto 0);
         spi_sck : IN  std_logic;
         da_conv : IN  std_logic;
         addr : IN  std_logic_vector(3 downto 0);
         spi_miso : IN  std_logic;
         ready : OUT  std_logic;
         spi_mosi : OUT  std_logic;
         dac_cs : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal data : std_logic_vector(11 downto 0) := (others => '0');
   signal spi_sck : std_logic := '0';
   signal da_conv : std_logic := '0';
   signal addr : std_logic_vector(3 downto 0) := (others => '0');
   signal spi_miso : std_logic := '0';

 	--Outputs
   signal ready : std_logic;
   signal spi_mosi : std_logic;
   signal dac_cs : std_logic;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: DAC PORT MAP (
          data => data,
          spi_sck => spi_sck,
          da_conv => da_conv,
          addr => addr,
          spi_miso => spi_miso,
          ready => ready,
          spi_mosi => spi_mosi,
          dac_cs => dac_cs
        );
 
   -- No clocks detected in port list. Replace <clock> below with 
   -- appropriate port name 
 
   
   clk_process :process
   begin
		spi_sck <= '0';
		wait for 500 ns;
		spi_sck <= '1';
		wait for 500 ns;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100ms.
      wait for 100ns;	

		data <= "010010101010";
		da_conv <= '1';
		addr <= "0000";
		
      wait for 1 us;
		da_conv <= '0';

      -- insert stimulus here 

      wait;
   end process;

END;
