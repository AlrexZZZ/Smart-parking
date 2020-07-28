package com.nineclient.utils;

import java.io.File;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class MP3Util {
	
    public static int getMp3TrackLength(File mp3File) {  
        try {  
            MP3File f = (MP3File)AudioFileIO.read(mp3File);  
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();  
            return audioHeader.getTrackLength();      
        } catch(Exception e) {  
            return -1;  
        }  
    }  
}
