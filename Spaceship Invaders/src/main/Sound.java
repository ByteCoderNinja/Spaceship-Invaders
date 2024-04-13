package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound
{
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound()
    {
        soundURL[0] = getClass().getClassLoader().getResource("sound/Fast Forward Space Travelling  Sound Effect WAV.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/Sound Effects - Walking on Solid Metal WAV.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/Space ship door opening WAV.wav");
    }

    public void setFile(int i)
    {
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e)
        {

        }
    }

    public void play()
    {
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }
}
