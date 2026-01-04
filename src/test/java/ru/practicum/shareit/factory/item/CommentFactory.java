package ru.practicum.shareit.factory.item;

import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.ModelFactory;
import ru.practicum.shareit.features.item.model.Comment;

import java.time.LocalDateTime;

public class CommentFactory extends ModelFactory<Comment> {
    private final FactoryProvider factories;

    public CommentFactory(FactoryProvider factories) {
        super(factories.repositories().comment());
        this.factories = factories;
    }

    @Override
    public Comment make(Comment attributes) {
        attributes = attributes != null ? attributes : new Comment();

        Comment comment = new Comment(getValueOrDefault(attributes.getText(), makeUniqueWord()));
        comment.setItem(getValueOrDefault(attributes.getItem(), factories.item().make()));
        comment.setAuthor(getValueOrDefault(attributes.getAuthor(), factories.user().make()));
        comment.setCreated(getValueOrDefault(attributes.getCreated(), LocalDateTime.now()));

        return comment;
    }

    @Override
    public Comment create(Comment attributes) {
        attributes = attributes != null ? attributes : new Comment();

        Comment model = make(attributes);
        model.setItem(getValueOrDefault(attributes.getItem(), factories.item().create()));
        model.setAuthor(getValueOrDefault(attributes.getAuthor(), factories.user().create()));

        return repository.saveAndFlush(model);
    }
}

