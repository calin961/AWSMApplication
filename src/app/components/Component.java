package app.components;

import app.services.prototype.Product;

public class Component extends Product {

    public final String provider;
    public final Boolean delivered;
    public final String comments;

    public Component(Component component) {
        super(component.id,
                component.categoryId,
                component.categoryName,
                component.name,
                component.amount,
                component.price,
                component.paid,
                component.date,
                component.image);

        this.provider = component.provider;
        this.delivered = component.delivered;
        this.comments = component.comments;
    }

    public Component(int id,
                     int categoryId,
                     String categoryName,
                     String name,
                     int amount,
                     int price,
                     Boolean paid,
                     String date,
                     String image,
                     String provider,
                     Boolean delivered,
                     String comments) {
        super(id, categoryId, categoryName, name, amount, price, paid, date, image);
        this.provider = provider;
        this.paid = paid;
        this.delivered = delivered;
        this.comments = comments;
    }
}
