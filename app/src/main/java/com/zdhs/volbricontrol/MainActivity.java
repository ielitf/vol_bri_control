package com.zdhs.volbricontrol;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.example.demo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button back, voiceUp, voiceDown, lightUp, lightDown;
    private AudioManager audioManager;
    private int brightness;//当前屏幕亮度
    private int brightness_skip = 10;//当前屏幕亮度调节跨度
    //    private ConsumerIrManager consumerIrManager;
    private int[] pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = findViewById(R.id.back);
        voiceUp = findViewById(R.id.voice_up);
        voiceDown = findViewById(R.id.voice_down);
        lightUp = findViewById(R.id.light_up);
        lightDown = findViewById(R.id.light_down);
        back.setOnClickListener(this);
        voiceUp.setOnClickListener(this);
        voiceDown.setOnClickListener(this);
        lightUp.setOnClickListener(this);
        lightDown.setOnClickListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        consumerIrManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
//        Log.i("===========", "是否支持红外线= " + consumerIrManager.hasIrEmitter());
    }

    /**
     * 通过遥控器按键控制音量和屏幕亮度
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        if(consumerIrManager.hasIrEmitter() == true){//判断手机是否有红外模块
        Log.i("===========", "接受到的keyCode：" + keyCode);

        switch (keyCode) {
            case 21://菜单键menu
                volumeUp();//增大声音
                break;
            case 22://确认键
                volumeDown();//减小声音
                break;
            case 88://数字键8
                brightnessUp();//增大亮度
                break;
            case 20://数字键0
                brightnessDown();//减小亮度
                break;
            default:
                break;
        }
//        }else{
//            Toast.makeText(this,"不支持红外线",Toast.LENGTH_SHORT).show();
//        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 通过点击app里button控制音量和屏幕亮度
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_up:
                volumeUp();//增大声音
                break;
            case R.id.voice_down:
                volumeDown();//减小声音
                break;
            case R.id.light_up:
                brightnessUp();//增大亮度
                break;
            case R.id.light_down:
                brightnessDown();//减小亮度
                break;
            case R.id.back:
                Log.i("===========", "退出了没？ ");
                System.exit(0);
            default:
                break;
        }
    }

    /**
     * 增加音量
     */
    private void volumeUp() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    /**
     * 减小音量
     */
    private void volumeDown() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    /**
     * 增加屏幕亮度
     */
    private void brightnessUp() {
        brightness = ScreenBrightUtils.getScreenBrightness(this);
        Log.i("===========", "当前屏幕亮度= " + brightness);
        brightness = brightness + brightness_skip;
        if (brightness >= 255) {
            brightness = 255;
        }
        ScreenBrightUtils.setBrightness(this, brightness);
        ScreenBrightUtils.saveBrightness(this, brightness);
        brightness = ScreenBrightUtils.getScreenBrightness(this);
        Log.i("===========", "调节后屏幕亮度= " + brightness);
    }

    /**
     * 减小屏幕亮度
     */
    private void brightnessDown() {
        brightness = ScreenBrightUtils.getScreenBrightness(this);
        Log.i("===========", "当前屏幕亮度= " + brightness);
        brightness = brightness - brightness_skip;
        if (brightness <= 1) {
            brightness = 1;
        }
        ScreenBrightUtils.setBrightness(this, brightness);
        ScreenBrightUtils.saveBrightness(this, brightness);
        brightness = ScreenBrightUtils.getScreenBrightness(this);
        Log.i("===========", "调节后屏幕亮度= " + brightness);
    }
}
