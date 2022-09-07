package pl.estrix.commons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

/**
 * Created by Kamil on 30-07-2020.
 */
@ToString(callSuper = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class ErrorResult<T> extends Result<T> {

	private ErrorResult(){
	}

	public ErrorResult(ErrorCode errorCode, String errorMessage, T data){
		this.data = data;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public boolean isError(){return true;}
}
