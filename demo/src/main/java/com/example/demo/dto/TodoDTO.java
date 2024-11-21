package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import com.example.demo.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String id;
	private String title;
	private boolean done;

	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}

	public TodoEntity toEntity(UserEntity userEntity) {
		return TodoEntity.builder()
				.id(this.id)
				.user(userEntity)
				.title(this.title)
				.done(this.done)
				.build();
	}

	public TodoEntity toEntity(String id, UserEntity userEntity) {
		return TodoEntity.builder()
				.id(id)
				.user(userEntity)
				.title(this.title)
				.done(this.done)
				.build();
	}
}

