package com.example.sample.service;

import com.example.sample.domain.SpeakerRepository;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

	private final SpeakerRepository speakerRepository;

	public SampleService(SpeakerRepository speakerRepository) {
		this.speakerRepository = speakerRepository;
	}



}
