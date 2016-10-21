/*
1. Checked Exception Spring Transaction에서 자동 RollBack하지 않기 때문에 필요시 Rollback-for="SqlException"(만 Exception으로 지정시 기대하지 않은 곳에서 롤백)
 - Spring JDBC, MyBatis와 같은 곳에서는 SQLException(Checked Exception)을 발생하지않고 RuntimeException 발생
 - Spring Framework에서 SQLExcetption과 같은 Checked Exception 남용으로 인한 피해를 줄이고자 DataAccessException(RunTimeException) 포장해서 제공

2. Spring, Hiberate 프레임워크에서도 가능하면 Checked Exception을 사용하지 않는 방향으로 진행

3. Clean Code 
	Checked exception VS Unchecked Exception(참조 1)
	예외처리에 드는 비용 대비 이득을 생각해봐야 한다.
	예시
	1. 특정 메소드에서 checked exception을 throw하고
	2. 3단계(메소드 콜) 위의 메소드에서 그 exception을 catch한다면
	3. 모든 중간단계 메소드에 exception을 정의해야 한다.(자바의 경우 메소드 선언에 throws 구문을 붙이는 등)
	Open/Closed Principle violation(참조 2)
	상위 레벨 메소드에서 하위 레벨 메소드의 디테일에 대해 알아야 하기 때문에 캡슐화 또한 깨진다.
	필요한 경우 checked exceptions를 사용해야 되지만 일반적인 경우 득보다 실이 많다.

4. chatch문으로 checked Exception을 처리하고 Unchecked Exception을 throws한다. 이런 방법이 Method Signatures를 Clean하게 한다. 

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
