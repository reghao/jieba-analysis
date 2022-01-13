package com.huaban.analysis.jieba.reghao;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-01-13 14:39:14
 */
public class TextAnalyzer {
    private final JiebaSegmenter segmenter;

    public TextAnalyzer() {
        this.segmenter = new JiebaSegmenter();
    }

    public TextAnalyzer(String userDict) {
        this.segmenter = new JiebaSegmenter();
        WordDictionary.getInstance().init(Paths.get(userDict));
    }

    public List<String> split2Words(String sentence) {
        return split(sentence).stream()
                .filter(word -> word.length() > 1)
                .filter(this::containTarget)
                .collect(Collectors.toList());
    }

    private boolean containTarget(String str) {
        if (str.isBlank()) {
            return false;
        }

        // 包含数字、字母或中文字符
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    private List<String> split(String sentence) {
        return segmenter.sentenceProcess(sentence);
    }
}
