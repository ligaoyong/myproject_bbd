package com.kedavoice;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.iflytek.cloud.speech.*;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文字转语音demo
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/11/18 18:22
 */
public class Demo {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    //合成监听器
    private static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
        //progress为合成进度0~100
        @Override
        public void onBufferProgress(int progress) {
        }

        //会话合成完成回调接口
        //uri为合成保存地址，error为错误信息，为null时表示合成会话成功
        @Override
        public void onSynthesizeCompleted(String uri, SpeechError error) {
            System.out.println("error : "+error);
            System.out.println(uri);
            countDownLatch.countDown();
        }

        @Override
        public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

        }
    };

    static {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=5dd26ff5 ");
    }

    public static void main(String[] args) throws Exception {
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0~100
        mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0~100

        String path = "C:\\Users\\dcb\\Desktop\\语音测试股样本.txt";
        List<String> strings = Files.readLines(new File(path), Charset.forName("utf-8"));
        //String content = String.join("", strings);

        //排除非汉字字符
        String result = strings.stream()
                .map(s -> s.split(""))
                .flatMap(strings1 -> Stream.of(strings1))
                .map(s -> s.charAt(0))
                .filter(character -> {
//                    int n = (int) character;
//                    if (!(19968 <= n && n < 40869)) {
//                        return false;
//                    }
//                    return true;
                    Character.UnicodeBlock ub = Character.UnicodeBlock.of(character);
                    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                        return true;
                    }
                    return false;
                })
                .map(character -> String.valueOf(character))
                .collect(Collectors.joining(""));

        //保存过滤后的数据
//        String resultPath = "C:\\Users\\dcb\\Desktop\\result.txt";
//        FileUtil.writeAsString(new File(result), result);
        //Files.newWriter(new File(result), StandardCharsets.UTF_8).write(result);
        System.out.println(result);


        //3.开始合成
        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
        mTts.synthesizeToUri(result,
                "D:\\Work\\dcb\\myproject\\transaction-logs\\aaa.pcm", synthesizeToUriListener);
        countDownLatch.await();
        Pcm2Wav.convertAudioFiles("D:\\Work\\dcb\\myproject\\transaction-logs\\aaa.pcm",
                "D:\\Work\\dcb\\myproject\\transaction-logs\\aaa.wav");
    }
}
