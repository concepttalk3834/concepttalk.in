package com.concept.talk.util;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
public class Message {
	private String content;
	@Builder.Default
	private MessageType type = MessageType.blue;
}
