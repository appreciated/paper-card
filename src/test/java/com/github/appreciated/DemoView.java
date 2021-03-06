package com.github.appreciated;

import com.github.appreciated.card.*;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@Route("")
//@Theme(value = Lumo.class,variant = Lumo.DARK)
//@Theme(value = Material.class, variant = Material.LIGHT)
@Theme(value = Material.class, variant = Material.DARK)
@BodySize(width = "100%", height = "100%")
public class DemoView extends VerticalLayout {

    int currentElevation = 0;
    int currentElevation1 = 0;
    int currentElevation2 = 0;
    int currentElevation3 = 0;

    public DemoView() {

        setMargin(false);

        ClickableCard c = new ClickableCard().withWidth("500px").withHeight("500px");
        Div div = new Div();
        div.getStyle().set("background", "red");
        div.setSizeFull();
        c.add(div);

        Card card = new Card(
                // if you don't want the title to wrap you can set the whitespace = nowrap
                new TitleLabel("Example Title").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"),
                new SecondaryLabel("Some secondary text"),
                new IconItem(getIcon(), "Icon Item title", "Icon Item description"),
                new Item("Item title", "Item description"),
                new Image("/frontend/bg.png", "bg.png"),
                new Actions(
                        new ActionButton("Action 1", event -> {/* Handle Action*/}),
                        new ActionButton("Action 2", event -> {/* Handle Action*/})
                )
        ).withWidth("300px");

        StatefulCard scard = new StatefulCard(
                new IconItem(getIcon(), "Icon Item title", "Icon Item description")
        ).withWidth("300px");
        scard.addClickListener(event -> scard.setSelected(true));

        StatefulCardGroup<MyStatefulCard> group = new StatefulCardGroup<>(
                new MyStatefulCard(1),
                new MyStatefulCard(2),
                new MyStatefulCard(3),
                new MyStatefulCard(4),
                new MyStatefulCard(5),
                new MyStatefulCard(6),
                new MyStatefulCard(7)
        ).withStateChangedListener(statefulCard -> UI.getCurrent().access(() -> Notification.show("MyStatefulCard " + statefulCard + " selected!")));
        group.setState(group.getCards().get(3));
        add(
                c,
                card,
                //getCard(false),
                getCard(true),
                getCard(true),
                getClickableCard(false),
                getClickableCard(true),
                getClickableCard(true),
                getClickableCard(true),
                getClickableImageCard(false),
                getClickableImageCard(true),
                getClickableImageCard(true),
                getClickableNoElevationOnClickImageCard(false),
                getClickableNoElevationOnClickImageCard(true),
                getClickableNoElevationOnClickImageCard(true),
//                new Item("Test text"),
//                new Item("Test title", "Test description"),
//                new IconItem(getIcon(), "Test text"),
//                new IconItem(getIcon(), "Test Title", "Test Description")
                scard,
                group,
                new Button("Unset StatefulFocus", buttonClickEvent -> group.setState(null))
        );
    }

    private Component getClickableNoElevationOnClickImageCard(boolean b) {
        RippleClickableCard card = getClickableImageCard(b).withElevationOnActionEnabled(false);
        card.setElevation(currentElevation3++);
        return card;
    }

    private Card getCard(boolean hasAction) {
        Card card =
                hasAction ? new Card(new TitleLabel("Test Title"),
                        new Image("/frontend/bg.png", "bg.png"), getCardUnselectableContent(), getActions()) :
                        new Card(getCardUnselectableContent());
        card.setWidth("300px");
        card.setElevation(currentElevation++);
        return card;
    }

    private Icon getIcon() {
        Icon icon = VaadinIcon.VAADIN_V.create();
        icon.getStyle()
                .set("width", "45px")
                .set("height", "45px")
                .set("color", "var(--lumo-primary-color)");
        return icon;
    }

    private ClickableCard getClickableCard(boolean hasAction) {
        ClickableCard card = hasAction ? new ClickableCard(event -> Notification.show("Clicked!"), getCardSelectableContent(), getActions()) : new ClickableCard(event -> Notification.show("Clicked!"), getCardSelectableContent());
        card.setWidth("300px");
        card.setElevation(currentElevation1++);
        card.setElevationOnActionEnabled(true);
        return card;
    }

    private RippleClickableCard getClickableImageCard(boolean hasAction) {
        RippleClickableCard card = hasAction ? new RippleClickableCard(event -> Notification.show("Clicked!"), getClickableImageCardActionContent()) :
                new RippleClickableCard(event -> Notification.show("Clicked!"), getClickableImageCardContent());
        card.setWidth("300px");
        card.setElevation(currentElevation2++);
        return card;
    }

    private Actions getActions() {
        return new Actions(
                new ActionButton("Action 1", buttonClickEvent -> Notification.show("Action 1 clicked")),
                new ActionButton("Action 2", buttonClickEvent -> Notification.show("Action 2 clicked"))
        );
    }

    private Component getCardUnselectableContent() {
        return new IconItem(getIcon(), "Normal CardsdfgsdfgjkÖASJDKLÖjasklödjklASJDKLJKLASDF", "I can normal card. I show content and provide some actions below");
    }

    private Component getCardSelectableContent() {
        return new IconItem(getIcon(), "Clickable Card", "I am a clickable card. I can be clicked, I show content and provide some actions below");
    }

    private Component[] getClickableImageCardContent() {
        return new Component[]{new Image("/frontend/bg.png", "bg.png"), new PrimaryLabel("Clickable Image Card")};
    }

    private Component[] getClickableImageCardActionContent() {
        IronCollapse collapse = new IronCollapse(new VerticalLayout(getExampleContent(), getExampleContent(), getExampleContent()));
        collapse.setWidth("100%");
        return new Component[]{new Image("/frontend/bg.png", "bg.png"), collapse, new Actions(new ActionButton("Expand", buttonClickEvent -> collapse.toggle()))};
    }

    private Component getExampleContent() {
        return new IconItem(getIcon(), "Example", "I got expanded with Iron-Collapse!");
    }

    class MyStatefulCard extends StatefulCard {
        private int identifier;

        public MyStatefulCard(int identifier) {
            this.identifier = identifier;
            add(new IconItem(getIcon(), "Icon Item " + identifier, "Icon Item description sjskdfgjksdfjglöksdfg")
                    .withWhiteSpaceNoWrap()
                    .withPadding("5px"));
            withElevation(0)
                    .withWidth("300px")
                    .withBorderRadius("0px");
        }

        public int getIdentifier() {
            return identifier;
        }
    }
}
