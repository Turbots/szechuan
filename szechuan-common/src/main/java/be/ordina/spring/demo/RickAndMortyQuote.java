package be.ordina.spring.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import static be.ordina.spring.demo.RickAndMortyCharacter.*;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RickAndMortyQuote {

	I_WANT_MY_SZECHUAN_SAUCE(RICK),
	WUBBA_LUBBA_DUB_DUB(RICK),

	IM_MR_MEESEEKS_LOOK_AT_ME(MR_MEESEEKS),
	HEY_THERE_IM_MR_MEESEEKS(MR_MEESEEKS),
	LOOK_AT_ME_IM_MR_MEESEEKS(MR_MEESEEKS),

	EXISTENCE_IS_PAIN(MR_MEESEEKS),
	NOW_I_LL_NEVER_DIE(MR_MEESEEKS),

	YES_SIRREEE(MR_MEESEEKS),
	OOOH_OKAY(MR_MEESEEKS),
	OOOH_YEAH_CAN_DO(MR_MEESEEKS),

	PLEASE_GIVE_ME_SOME_SZECHUAN_SAUCE(MR_MEESEEKS),

	ALL_DONE(MR_MEESEEKS),

	CAN_I_TAKE_YOUR_ORDER_PLEASE(MCDONALDS),
	SORRY_NO_LUCK(MCDONALDS),
	YOU_ARE_A_WINNER(MCDONALDS);

	private RickAndMortyCharacter author;

	RickAndMortyQuote(RickAndMortyCharacter author) {
		this.author = author;
	}

	public String getMessage() {
		return this.name();
	}
}
