/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1.Model;

import prac1.exceptions.NoDurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author GrupD
 * @author Txell Llanas
 */
public class Song {

    private final File file;
    private AudioFileFormat fileFormat;
    private String index;
    private String title;
    private String duration;

    public Song(File file) {
        this.file = file;
    }
  
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    
    /**
     * Mostra el nom de la cançó seleccionada
     * @return Mostra el nom de l'arxiu (Títol de la cançó)
     * 
     * @author Txell Llanas
     */
    public String getTitle() {
        int extensio = file.getName().indexOf(".");
        title = file.getName().substring(0, extensio);
        return title;
    }

    /**
     * Mostra la duració total de la cançó seleccionada amb el format mm:ss
     * @return
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws prac1.exceptions.NoDurationException
     * 
     * @author Txell Llanas
     */
    public String getDuration() throws UnsupportedAudioFileException, IOException, NoDurationException {

            fileFormat = AudioSystem.getAudioFileFormat(file);
            System.out.println("format: " + fileFormat);
        
            if (fileFormat instanceof AudioFileFormat) {
                Map<?, ?> properties = ((AudioFileFormat) fileFormat).properties();
                String key = "duration";
                Long microseconds = (Long) properties.get(key);
                int mili = (int) (microseconds / 1000);
                int sec = (mili / 1000) % 60;
                int min = (mili / 1000) / 60;

                if (sec == 0 && min == 0) {
                    throw new NoDurationException("La duració de l'arxiu d'àudio"
                                              + " ha de ser superior a 00:00.");
                }

                duration = String.format("%02d:%02d", min, sec);

            } else {
                throw new UnsupportedAudioFileException();
            }
        
        return duration;
    }
}