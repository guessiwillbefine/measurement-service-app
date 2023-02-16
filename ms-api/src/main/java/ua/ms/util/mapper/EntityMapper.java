package ua.ms.util.mapper;

public interface EntityMapper<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
