package com.assignment2.chat.application.repositories;

import com.assignment2.chat.application.entities.ChatEntity;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ChatRepository extends PagingAndSortingRepository<ChatEntity, Long> {
}
