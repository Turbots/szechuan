package be.ordina.spring.demo;

import com.fasterxml.jackson.annotation.JsonFormat;

import static be.ordina.spring.demo.RickAndMortyCharacter.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RickAndMortyQuote {

	I_WANT_MY_SZECHUAN_SAUCE(RICK, "I want my szechuan sauce!"),
	WUBBA_LUBBA_DUB_DUB(RICK, "Wubba Lubba Dub Dub!"),

	MR_MEESEEKS_SPAWN(MR_MEESEEKS, "I'm Mr Meeseeks, look at me"),

	IM_MR_MEESEEKS_LOOK_AT_ME(MR_MEESEEKS, "I'm Mr Meeseeks, look at me"),
	HEY_THERE_IM_MR_MEESEEKS(MR_MEESEEKS, "Hey there, I'm Mr Meeseeks"),
	LOOK_AT_ME_IM_MR_MEESEEKS(MR_MEESEEKS, "Look at me, I'm Mr Meeseeks"),

	EXISTENCE_IS_PAIN(MR_MEESEEKS, "Existence is pain!"),
	NOW_I_LL_NEVER_DIE(MR_MEESEEKS, "Now I'll never die"),

	YES_SIRREEE(MR_MEESEEKS, "Yes Sirreee"),
	OOOH_OKAY(MR_MEESEEKS, "Ooh Okay"),
	OOOH_YEAH_CAN_DO(MR_MEESEEKS, "Ooh Yeah Can Do"),

	PLEASE_GIVE_ME_SOME_SZECHUAN_SAUCE(MR_MEESEEKS, "Give me some szechuan sauce"),

	ALL_DONE(MR_MEESEEKS, "All done!"),

	CAN_I_TAKE_YOUR_ORDER_PLEASE(MCDONALDS, "Can I take your order please?"),
	SORRY_NO_LUCK(MCDONALDS, "You lose!"),
	YOU_ARE_A_WINNER(MCDONALDS, "You are a winner!");

	private RickAndMortyCharacter author;
	private String text;

	RickAndMortyQuote(RickAndMortyCharacter author, String text) {
		this.author = author;
		this.text = text;
	}

	public String getMessage() {
		return this.name();
	}

	public RickAndMortyCharacter getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}
}
