package Iteratie13.view;

import iteratie12.model.Parchis;

public class ParchisPresenter {
    private Parchis model;
    private ParchisView view;
    public ParchisPresenter(
            Parchis model,
            ParchisView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }
    private void addEventHandlers() {
// Koppelt event handlers (anon. inner klassen)
// aan de controls uit de view.
// Event handlers: roepen methodes aan uit het
// model en zorgen voor een update van de view.
    }
    private void updateView() {
// Vult de view met data uit model
    }
}
