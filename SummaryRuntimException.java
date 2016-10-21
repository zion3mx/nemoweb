/*
1. Checked Exception Spring Transaction에서 자동 RollBack하지 않기 때문에 필요시 Rollback-for="SqlException"(만 Exception으로 지정시 기대하지 않은 곳에서 롤백)
 - Spring JDBC, MyBatis와 같은 곳에서는 SQLException(Checked Exception)을 발생하지않고 RuntimeException 발생
 - Spring Framework에서 SQLExcetption과 같은 Checked Exception 남용으로 인한 피해를 줄이고자 DataAccessException(RunTimeException) 포장해서 제공

2. Spring, Hiberate 프레임워크에서도 가능하면 Checked Exception을 사용하지 않는 방향으로 진행
*/


/*******************************************************************************
   AS-IS
*******************************************************************************/
public *** addToCart(Long purcharseNo, PurcharseInfo purcharseInfo) throws ****ServiceException{
	
	if( StringUtils.isNullOrEmpty(purcharseInfo.getRequeiredInfo()) ){
		throw new LAMPServiceException(****ServiceException.ErrorCode.REQUIRED_0001);
	}

	Purcharse purcharse = purcharseRepository.findById(purcharseNo);

	if( StringUtils.isNullOrEmpty(purcharse) ){
		throw new ****ServiceException(****ServiceException.ErrorCode.REQUIRED_0002);
	}
}
 

public class ****ServiceException extends Exception{
	public enum ErrorCode {
		REQUIRED_0001("REQUIRED_0001", "The purcharseInfo has not exist"),
		REQUIRED_0002("REQUIRED_0002", "The purcharseRepository has not exist"),
	....
}


/*******************************************************************************
   TO-BE
*******************************************************************************/
public *** addToCart(Long purcharseNo, PurcharseInfo purcharseInfo){

	if( StringUtils.isNullOrEmpty(purcharseInfo.getRequeiredInfo()) ){
		occurredCustomRuntimeException("name", "value") );
	}

	Purcharse purcharse = purcharseRepositiry.findById(purcharseNo);

	if( StringUtils.isNullOrEmpty(purcharse) ){
		occurredCustomRuntimeException("name", "value" );
	}
}


private occurredCustomRuntimeException( String name, String value) {
	....
 	throw new ****ServiceRuntimeException(****ServiceException.ErrorCode.REQUIRED_000*);
}



public class ****ServiceRuntimeException extends RunTimeException{
	public enum ErrorCode {
		REQUIRED_0001("REQUIRED_0001", "The purcharseInfo has not exist"),
		REQUIRED_0002("REQUIRED_0002", "The purcharseRepository has not exist"),

	.....

}
