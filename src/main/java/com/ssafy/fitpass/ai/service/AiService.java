package com.ssafy.fitpass.ai.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final OpenAiChatModel openAiChatModel;
    private final ThreadLocal<StringBuilder> threadLocalSB = ThreadLocal.withInitial(StringBuilder::new);

    private final String prompt_findHateText = "너는 사이버 수사대야. 입력되는 글에 욕설이나 남을 비난, 비방하는 내용, 또는 혐오 표현이 들어있는지 찾아주는 역할을 하고 있어.\n" +
            "아래의 <내용>에 욕설, 남을 비방, 비난하는 내용, 혐오 표현이 포함되어 있으면 너는 \"false\"라고 출력하고 없다면 \"true\"라고 출력해줘\n" +
            "<내용>\n";

    AiService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public boolean isContentAppropriate(String text) {
        StringBuilder sb = threadLocalSB.get();
        sb.setLength(0);
        sb.append(prompt_findHateText).append(text);

        String result = openAiChatModel.call(sb.toString());

        if("true".equals(result)) {
            return true;
        }
        return false;
    }

    private final String prompt_generateTitle = "너는 재밌는 짧은 글을 쓰는 것으로 유명한 작가야.\n " +
            "아래의 입력된 <내용>을 보고 내용에 맞는 제목을 지어줘.\n" +
            "(제한사항: 제목은 50자를 넘길 수 없고, 재치가 넘치고 제목을 보면 내용이 유추될 수 있었으면 좋겠어. 제목을 결정하는 데에 가장 중요한 건 재치와 유머야.)\n" +
            "<내용>";

    public String createTitle(String content) {
        StringBuilder sb = threadLocalSB.get();
        sb.setLength(0);
        sb.append(prompt_generateTitle).append(content);

        return openAiChatModel.call(sb.toString());
    }
}
