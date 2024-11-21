package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService service;

	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		String str = service.testService(); // 테스트 서비스 사용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// ResponseEntity.ok(response) 를 사용해도 상관 없음
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal UserEntity userEntity, @RequestBody TodoDTO dto) {
		try {

			TodoEntity entity = dto.toEntity(null, userEntity);
			List<TodoEntity> entities = service.create(entity);
			return getDtoResponseEntity(entities);
		} catch (Exception e) {
			return getErrorResponse(e);
		}
	}

	private static ResponseEntity<ResponseDTO<TodoDTO>> getErrorResponse(Exception e) {
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build();
		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal UserEntity userEntity) {
		List<TodoEntity> entities = service.retrieve(userEntity);
		return getDtoResponseEntity(entities);
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal UserEntity userEntity, @RequestBody TodoDTO dto) {
		TodoEntity entity = dto.toEntity(userEntity);
		List<TodoEntity> entities = service.update(entity);
		return getDtoResponseEntity(entities);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal UserEntity userEntity, @RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = dto.toEntity(userEntity);
			List<TodoEntity> entities = service.delete(entity);
			return getDtoResponseEntity(entities);
		} catch (Exception e) {
			return getErrorResponse(e);
		}
	}

	private ResponseEntity<ResponseDTO<TodoDTO>> getDtoResponseEntity(List<TodoEntity> entities) {
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).toList();
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
}
