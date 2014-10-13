package com.iluminatic.modelo;

import java.math.BigDecimal;

public class Calculos {
	
	
	public BigDecimal convertirBytesToMegas(long bytes){
		
		BigDecimal nro = new BigDecimal (bytes);
		nro.setScale(2,BigDecimal.ROUND_UP);
		
		BigDecimal mbs = new BigDecimal(1048576);
		nro.setScale(2,BigDecimal.ROUND_UP);
		
		return nro.divide(mbs).setScale(2,BigDecimal.ROUND_UP);
		
		
	}
	
	public BigDecimal calulcarPorcentaje (BigDecimal mbConsumidos, String mbPlan){
		
		
		BigDecimal mbPlanConfig = new BigDecimal(mbPlan);
		mbPlanConfig.setScale(2,BigDecimal.ROUND_UP);
		
		
		return mbConsumidos.divide(mbPlanConfig).setScale(2,BigDecimal.ROUND_UP);
	}
	

}
