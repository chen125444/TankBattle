package com.jr.tankbattle.util;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private static Sound instance = new Sound();

    private MediaPlayer bgmPlayer;
    private double volume=0.5;
    private String bgmName;

    private Media[] soundFiles = new Media[10];

    private int num=-1; //记录正在播放的bgm序号

    public Sound() {
        soundFiles[0] = new Media(getClass().getResource("/com/jr/tankbattle/sound/music1.mp3").toExternalForm());
        soundFiles[1] = new Media(getClass().getResource("/com/jr/tankbattle/sound/music2.mp3").toExternalForm());
        soundFiles[2] = new Media(getClass().getResource("/com/jr/tankbattle/sound/music3.mp3").toExternalForm());
        soundFiles[3] = new Media(getClass().getResource("/com/jr/tankbattle/sound/music4.mp3").toExternalForm());
        soundFiles[4] = new Media(getClass().getResource("/com/jr/tankbattle/sound/music5.mp3").toExternalForm());
    }

    public static Sound getInstance() {
        return instance;
    }

    public MediaPlayer getBgmPlayer() {
        return bgmPlayer;
    }

    public void setVVolume(double volume) { //记录玩家更改的音量
        this.volume = volume;
    }

    public void setBgmName(String bgmName) { //记录玩家更改的bgm
        this.bgmName = bgmName;
    }

    public String getBgmName() {
        return bgmName;
    }

    public void BgmChg(int num) { //bgm切换
        if (num >= 0 && num < soundFiles.length) {
            if(this.num!=num) { //避免切换界面引起同一个bgm重新播放
                if (bgmPlayer != null && bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    bgmPlayer.stop();
                }
                bgmPlayer = new MediaPlayer(soundFiles[num]);
                bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                bgmPlayer.setVolume(volume);
                bgmPlayer.play();

                this.num=num;
            }
        }
    }
}
