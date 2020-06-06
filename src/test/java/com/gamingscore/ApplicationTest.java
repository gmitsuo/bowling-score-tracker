package com.gamingscore;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ApplicationTest {

	@Test
	void noInputFileShouldThrowException() {

		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> Application.main(new String[]{}))
		.withMessage("Text file input argument is missing");
	}

	@Test
	void notExistingFileInputShouldThrowException() {

		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> Application.main(new String[]{"gameinputs/not-existing-file.txt"}))
		.withMessage("Text file input argument is invalid. File gameinputs/not-existing-file.txt not found or corrupt");
	}

	@Test
	void validInput() {

		String path = this.getClass().getClassLoader()
		.getResource("gameinputs/two-players.txt").getFile();

		Application.main(new String[]{path});
	}
}