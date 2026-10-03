/*******************************************************************************
 * Solitaire
 *
 * Copyright (C) 2025 by Martin P. Robillard
 *
 * See: https://github.com/prmr/Solitaire
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *******************************************************************************/
package ca.mcgill.solitaire.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of 52 cards in a predetermined winnable order.
 *
 * The deck supports a small number of winnable configurations. Configuration
 * 1 is the original verified order. Configurations 2, 3, and 4 are suit-
 * transformed versions of that order and have also been verified with the
 * GreedyPlayingStrategy.
 */
public class Deck {
    private static final int DEFAULT_CONFIGURATION = 1;
    private static final int NUMBER_OF_CONFIGURATIONS = 4;

    private final int aConfiguration;
    private CardStack aCards;

    /**
     * Creates a deck using the default winnable configuration.
     */
    public Deck() {
        this(DEFAULT_CONFIGURATION);
    }

    /**
     * Creates a deck using the selected winnable configuration.
     *
     * @param pConfiguration The configuration number (1 to 4).
     * @pre pConfiguration >= 1 && pConfiguration <= 4
     */
    public Deck(int pConfiguration) {
        if (pConfiguration < 1 || pConfiguration > NUMBER_OF_CONFIGURATIONS) {
            throw new IllegalArgumentException("Configuration must be between 1 and 4.");
        }
        aConfiguration = pConfiguration;
        shuffle();
    }

    /**
     * Reinitializes the deck with the selected predetermined order.
     */
    public void shuffle() {
        List<Card> cards = new ArrayList<>();

        /*
         * Base DRAW ORDER.
         *
         * CardStack draws from the last element, so the list is reversed at
         * the end. The first card below is therefore the first card dealt.
         */
        add(cards, Rank.TWO, Suit.DIAMONDS);
        add(cards, Rank.FOUR, Suit.DIAMONDS);
        add(cards, Rank.NINE, Suit.DIAMONDS);
        add(cards, Rank.TEN, Suit.SPADES);
        add(cards, Rank.KING, Suit.CLUBS);
        add(cards, Rank.NINE, Suit.HEARTS);
        add(cards, Rank.ACE, Suit.CLUBS);
        add(cards, Rank.SEVEN, Suit.CLUBS);
        add(cards, Rank.TEN, Suit.HEARTS);
        add(cards, Rank.THREE, Suit.SPADES);
        add(cards, Rank.THREE, Suit.DIAMONDS);
        add(cards, Rank.EIGHT, Suit.HEARTS);
        add(cards, Rank.SIX, Suit.HEARTS);
        add(cards, Rank.FIVE, Suit.HEARTS);
        add(cards, Rank.FOUR, Suit.CLUBS);
        add(cards, Rank.TEN, Suit.DIAMONDS);
        add(cards, Rank.QUEEN, Suit.HEARTS);
        add(cards, Rank.FIVE, Suit.DIAMONDS);
        add(cards, Rank.SIX, Suit.DIAMONDS);
        add(cards, Rank.SIX, Suit.CLUBS);
        add(cards, Rank.EIGHT, Suit.DIAMONDS);
        add(cards, Rank.JACK, Suit.CLUBS);
        add(cards, Rank.SEVEN, Suit.SPADES);
        add(cards, Rank.EIGHT, Suit.CLUBS);
        add(cards, Rank.KING, Suit.SPADES);
        add(cards, Rank.SEVEN, Suit.HEARTS);
        add(cards, Rank.ACE, Suit.SPADES);
        add(cards, Rank.EIGHT, Suit.SPADES);
        add(cards, Rank.THREE, Suit.HEARTS);
        add(cards, Rank.FOUR, Suit.HEARTS);
        add(cards, Rank.JACK, Suit.HEARTS);
        add(cards, Rank.ACE, Suit.DIAMONDS);
        add(cards, Rank.QUEEN, Suit.DIAMONDS);
        add(cards, Rank.JACK, Suit.DIAMONDS);
        add(cards, Rank.QUEEN, Suit.CLUBS);
        add(cards, Rank.TWO, Suit.SPADES);
        add(cards, Rank.ACE, Suit.HEARTS);
        add(cards, Rank.TWO, Suit.CLUBS);
        add(cards, Rank.NINE, Suit.CLUBS);
        add(cards, Rank.THREE, Suit.CLUBS);
        add(cards, Rank.SEVEN, Suit.DIAMONDS);
        add(cards, Rank.QUEEN, Suit.SPADES);
        add(cards, Rank.FIVE, Suit.CLUBS);
        add(cards, Rank.SIX, Suit.SPADES);
        add(cards, Rank.KING, Suit.HEARTS);
        add(cards, Rank.FIVE, Suit.SPADES);
        add(cards, Rank.JACK, Suit.SPADES);
        add(cards, Rank.FOUR, Suit.SPADES);
        add(cards, Rank.KING, Suit.DIAMONDS);
        add(cards, Rank.NINE, Suit.SPADES);
        add(cards, Rank.TEN, Suit.CLUBS);
        add(cards, Rank.TWO, Suit.HEARTS);

        if (aConfiguration != 1) {
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                cards.set(i, Card.get(card.rank(), transformSuit(card.suit())));
            }
        }

        Collections.reverse(cards);
        aCards = new CardStack(cards);
    }

    private void add(List<Card> pCards, Rank pRank, Suit pSuit) {
        pCards.add(Card.get(pRank, pSuit));
    }

    /**
     * Applies a suit permutation to create additional winnable configurations.
     */
    private Suit transformSuit(Suit pSuit) {
        switch (aConfiguration) {
        case 2:
            // Swap the two black suits.
            if (pSuit == Suit.CLUBS) return Suit.SPADES;
            if (pSuit == Suit.SPADES) return Suit.CLUBS;
            return pSuit;
        case 3:
            // Swap the two red suits.
            if (pSuit == Suit.DIAMONDS) return Suit.HEARTS;
            if (pSuit == Suit.HEARTS) return Suit.DIAMONDS;
            return pSuit;
        case 4:
            // Swap both black and red suits.
            if (pSuit == Suit.CLUBS) return Suit.SPADES;
            if (pSuit == Suit.SPADES) return Suit.CLUBS;
            if (pSuit == Suit.DIAMONDS) return Suit.HEARTS;
            if (pSuit == Suit.HEARTS) return Suit.DIAMONDS;
            return pSuit;
        default:
            return pSuit;
        }
    }

    public void push(Card pCard) {
        assert pCard != null;
        aCards.push(pCard);
    }

    public Card draw() {
        assert !isEmpty();
        return aCards.pop();
    }

    public boolean isEmpty() {
        return aCards.isEmpty();
    }
}
