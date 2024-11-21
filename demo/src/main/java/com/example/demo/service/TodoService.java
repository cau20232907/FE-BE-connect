
package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRepository repository;

	public String testService() {
		// TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		// TodoEntity 저장
		repository.save(entity);
		// TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}

	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);

		repository.save(entity);

		log.info("Entity Id : {} is saved.", entity.getId());

		return retrieve(entity.getUser());
	}

	public List<TodoEntity> retrieve(final UserEntity userEntity) {
		return repository.findByUserId(userEntity.getId());
	}

	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);

		Optional<TodoEntity> original = repository.findById(entity.getId());
		original.ifPresent(todo -> {
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			repository.save(todo);
		});

		return retrieve(entity.getUser());
	}

	public List<TodoEntity> delete(final TodoEntity entity) {
		validate(entity);

		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("error deleting entity "+ entity.getId(), e);
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		return retrieve(entity.getUser());
	}

	private void validate(TodoEntity entity) {
		if (entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if (entity.getUser() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
