----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
----------------------------------------------------------------------------------
----------------------------------------------------------------------------------

entity Conversor is
----------------------------------------------------------------------------------
       generic (
-- sample_time es la constante que define el tiempo de muestreo de 
-- cada ciclo del reloj de entrada es 20ns 
-- Si se desea un tiempo de muestreo de 71us 
-- sample_time = 71us/20ns = 3571
       sample_time		: integer := 500000;
---------------------------------------------------------------------		
-- spi_sck_period es la constante que define la cantidad de
-- ciclos de reloj de entrada para generar el reloj de la comunicaci�
-- serial spi. Este nmero debe ser par.
       spi_sck_period	: integer := 6
		 
		 );
----------------------------------------------------------------------------------

   port(
	     
		  clk_50				   : in std_logic;--reloj interno de la FPGA C9
        rst					   : in std_logic; --reset 
        ----------------------------------------------------------------------------------
--		Pines compartidos por en SPI					
        spi_sck				: out std_logic;	--se�l de reloj compartida U16		
        spi_miso				: in std_logic;	--	spi de entrada FPGA
        spi_mosi				: out std_logic:= '0';-- SPI salida de FPGA
        ----------------------------------------------------------------------------------
--		Linear Tech LTC1407A-1 Dual A/D			-- IC20
        spi_adc_conv		   : out std_logic:='0';--	Habilita el ADC para comenzar a transmitir
		  
        ----------------------------------------------------------------------------------
--		Linear Tech LTC6912-1 Dual Amp		-- IC19
        spi_amp_cs			: out std_logic:= '1';	-- Pin de enable de AMP bajo
        spi_amp_shdn		   : out std_logic;		-- Pin p7 reset AMP
		  
        ----------------------------------------------------------------------------------
		  switch					: in std_logic:='0'; --Byte a mostrar en los Leds
		  ----------------------------------------------------------------------------------
		  
		  spi_dac_cs			: out std_logic;			-- Pin N8: Enable de ADC
		  spi_rom_cs			: out std_logic;			-- Pin U3: Enable de la ROM
		  strataflash_ce		: out std_logic;			-- Pin D16
		  strataflash_we		: out std_logic;			-- Pin D17
		  strataflash_oe		: out std_logic;			-- Pin C18
		  platformflash_oe	: out std_logic;        -- pin T3
		  
		  ----------------------------------------------------------------------------------
        channel_0_out	   : out std_logic_vector(15 downto 0);
	     channel_1_out	   : out std_logic_vector(15 downto 0);
        data_ready		   : out std_logic:='0';--actualiza los datos de conversion en el canal
		  ----------------------------------------------------------------------------------
		  leds_out				: out std_logic_vector(0 to 7):=(others=>'0')
        ----------------------------------------------------------------------------------
        );
end Conversor;
----------------------------------------------------------------------------------
----------------------------------------------------------------------------------

architecture archi_Conversor of Conversor is
    signal state		      : integer range 0 to 34:=0;-- los estados maquina
    signal channel_0_data	: std_logic_vector(15 downto 0); --canal datos ADC 0
    signal channel_1_data	: std_logic_vector(15 downto 0); --canal datos ADC 1
    signal counter_sample	: integer range 0 to sample_time:=0; --contador tiempo muestreo
    signal clk_sample		: std_logic:='0';--pulsos de reloj muestreo
    signal counter_sck	   : integer range 0 to spi_sck_period/2:=0;--contador 2
    signal clk_sck		   : std_logic:='0'; --se�l reloj interna dispositivos
    signal state_amp		   : integer range 0 to 9:=0;--maquina estados amp
    constant gain_amp	   : std_logic_vector(7 downto 0) := "00010001";
    signal data_ready_s	   : std_logic:='0';--actualiza el dato convertido

begin   


	spi_dac_cs <= '1';
	spi_rom_cs <= '1';	
	strataflash_ce <= '1';
	strataflash_we <= '1';
	strataflash_oe <= '1';
	platformflash_oe <= '0';
   data_ready<=data_ready_s;   
----------------------------------------------------------------------------------
--datos actualizados
----------------------------------------------------------------------------------

		process(channel_0_data, clk_50, switch)
		begin
			if switch = '0' then
				leds_out <= not(channel_0_data(7 downto 0));
			end if;
			if switch = '1' then
				leds_out <= not(channel_0_data(15 downto 8)) + "00100000";
			end if;
		end process;
----------------------------------------------------------------------------------


----------------------------------------------------------------------------------
    --ASIGNACION DE DATO CONVERTIDO--1
      process(clk_50)
         begin
	       if rising_edge(clk_50) then
		       if data_ready_s='1' then
			       channel_0_out <= channel_0_data;
		        	 channel_1_out <= channel_1_data;
		       end if;
	       end if;
     end process;

----------------------------------------------------------------------------------

----------------------------------------------------------------------------------
-- Proceso que lee el valor del ADC a traves de SPI usando máquina de estados
      process (clk_50,rst)
         begin
            if rst = '1' then
	            state <= 0;
	            spi_adc_conv <= '0';
	            channel_0_data <= (others => '0');
	            channel_1_data <= (others => '0');
	            data_ready_s <= '0';
       elsif rising_edge(clk_50) then
	          data_ready_s <= '0';
	        -- si ocurrió un periodo de spi, cambiar de estado ( rising_edge(spi_clk) )
	        if clk_sck = '1' and counter_sck = ((spi_sck_period/2)-1) then
		        state <= state + 1;
		        -- máquina de estados
		        case state is
			          when 0 => 
				           if clk_sample = '0' then
					           state <= 0;
				           else 
					           spi_adc_conv <= '1';
				           end if;
			           when 1 =>
				              channel_1_data <= (others=>'0');
				              spi_adc_conv <= '0';
			           when 3 =>
				            if spi_miso = '1' then
					            channel_0_data <= (others=>'1');
				            else
					            channel_0_data <= (others=>'0');
				            end if;
			          when 4 to 16 =>
		                     channel_0_data <= channel_0_data(14 downto 0) & spi_miso;
			          when 19 =>
				           if spi_miso = '1' then
					           channel_1_data <= (others=>'1');
				           else
					           channel_1_data <= (others=>'0');
				           end if;
			          when 20 to 31 =>
		                    channel_1_data <= channel_1_data(14 downto 0) & spi_miso;
			          when 32 =>
		                    channel_1_data <= channel_1_data(14 downto 0) & spi_miso;
					        data_ready_s <= '1'; -- Ya puede sacar el valor del ADC por la salida
			          when 34 =>
				              state <= 0;
			          when others =>
		        end case;
	       end if;
       end if;
    end process;
----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
-- Generacion de la se�l para el tiempo de muestreo (adc_clk)
-- adc_clk = 1 en 9pulsos y 0 en (sample_time-10)pulsos {clk_sample}
      process (clk_50,rst)
           begin
	          if rst = '1' then
		          counter_sample <= 0;
		          clk_sample <= '0';
	          elsif rising_edge(clk_50) then
		             counter_sample <= counter_sample + 1;
		             if counter_sample >= sample_time-1 then
			             counter_sample <= 0;
		             end if;
		          clk_sample <= '0';
		             if counter_sample <= 9 then 
			             clk_sample <= '1';
		             end if;
	          end if;

      end process;

---------------------------------------------------------------------------------- 

---------------------------------------------------------------------------------- 
-- generaci� de la se�l de reloj para la comunicaci� serial (spi_clk)
-- clk_sck=spi_clk= pulso de reloj, periodo=spi_sck_period*periodo_clk_entrada
        process (clk_50,rst)
           begin
	           if rst = '1' then
		        counter_sck <= 0; ---contador pulsos para la comunicacion
		        clk_sck <= '0'; --se�l de reloj dispositivo interno
	           elsif rising_edge(clk_50) then
		              counter_sck <= counter_sck + 1;--inicia el contador
		           if counter_sck >= ((spi_sck_period/2)-1) then --cuenta 2 periodos
			           counter_sck <= 0;--reinicia el contador
			           clk_sck <= not clk_sck;
		           end if;
	           end if;
           end process;
---------------------------------------------------------------------------------- 
    spi_sck <= clk_sck; --asigna el valor de se�l de reloj
---------------------------------------------------------------------------------- 
---------------------------------------------------------------------------------- 
-- Proceso para configurar la ganancia del amplificador
         process (clk_50,rst)
            begin
              if rst = '1' then
	             spi_amp_cs <= '1';--se�l de activacion ganancia
	             state_amp <= 0;--estados 
	             spi_mosi <= '0'; --datos ganancia
              elsif rising_edge(clk_50) then
	               if clk_sck = '1' and counter_sck = ((spi_sck_period/2)-1) then
		               state_amp <= state_amp + 1;
		            --máquina de estados
		            case state_amp is
			              when 0 => 
				                spi_amp_cs <= '0';
			              when 1 to 8 =>
				                spi_mosi <= gain_amp(8-state_amp);
			              when others =>
				                spi_amp_cs <= '1';
				                state_amp <= 9;
				                spi_mosi <= '0';
		            end case;
	               end if;
              end if;
         end process;

----------------------------------------------------------------------------------			
       spi_amp_shdn <= '0';
----------------------------------------------------------------------------------

----------------------------------------------------------------------------------
----------------------------------------------------------------------------------

end archi_Conversor;
----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
