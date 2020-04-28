package com.gamingscore.base;

public interface GameController<T> {

	/**
	 * @param lineIdx
	 * @param inputRow
	 * @return
	 */
	String[] checkInput(int lineIdx, String inputRow);

	/**
	 * @param rowInputs
	 * @return
	 */
	T toGameRound(String[] rowInputs);

	/**
	 * @param round
	 */
	void playRound(T round);

	/**
	 *
	 */
	void disployResults();
}
