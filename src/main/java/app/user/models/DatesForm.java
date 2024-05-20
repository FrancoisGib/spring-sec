package app.user.models;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class DatesForm {
	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date start;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date end;
}
