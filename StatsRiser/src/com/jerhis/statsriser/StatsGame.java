package com.jerhis.statsriser;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class StatsGame {

    int score;
	float time, lastDelta = 1;
    boolean results;
    float response;
    Question currentQuestion;
    final int mcYValues[] = {50,190,140,90,40}, fontCorrection = 43, fontHeight = 32;
	
	public StatsGame() {
	}
	
	public int update(float delta) {
        //delta = Math.min(delta, 0.025f);
        lastDelta = delta;
		time -= delta;

        if (time <= 0) {
            time = 0;
            results = true;
            score = currentQuestion.getScore(response);
            return -1;
        }

        return 0;
	}

    public void draw(BitmapFont f, SpriteBatch b, Texture checked, Texture unchecked) {
        if (results) drawResults(f,b);
        else drawRunning(f,b, checked, unchecked);
    }

    public void drawResults(BitmapFont f, SpriteBatch b) {

        if (currentQuestion instanceof QuestionMC) {
            int k = 0;
            for (; k < currentQuestion.question.size(); k++) {
                //f.draw(b, "Question: ", 40, 430);
                f.draw(b, currentQuestion.question.get(k), 10, 750-(k)*(fontHeight + 5));
            }
            f.draw(b, "Answer: ", 10, 750-(k+1)*(fontHeight + 5));
            f.draw(b, ((QuestionMC) currentQuestion).correct, 10, 750-(k+2)*(fontHeight + 5));
        }
        else if (currentQuestion instanceof QuestionFR) {
            int delimIndex = 0;
            int k = 0;
            for (; k < currentQuestion.question.size(); k++) {
                String init = currentQuestion.question.get(k), fin = "";
                for (int k2 = 0; k2 < init.length(); k2++) {
                    char ch = init.charAt(k2);
                    if (ch == '@') {
                        fin = fin + currentQuestion.delimiter.get(delimIndex++);
                    }
                    else {
                        fin += ch;
                    }
                }
                f.draw(b, fin, 60, 430-k*(fontHeight + 5));
            }
            f.draw(b, "Answer: ", 40, 430-(k)*(fontHeight + 5));
            f.draw(b, currentQuestion.answer + "", 60, 430-(k+1)*(fontHeight + 5));
        }

        boolean q = currentQuestion instanceof QuestionMC;

        if (response == -1)
            f.draw(b,"You didn't answer.",q ? 10 : 154,60);
        else if (response == currentQuestion.answer)
            f.draw(b,"You're correct.",q ? 10 : 50,60);
        else
            f.draw(b,"You were wrong.",q ? 10 : 10,60);

        //f.draw(b,"You scored " + currentQuestion.getScore(response) + " points.",170, 110);
    }

    private String convert(float f) {
        if (currentQuestion instanceof QuestionMC) {
            return (char)('A' - 1 + ((int)(10*f)) ) + "";
        }
        else return ""+f;
    }

    public void drawRunning(BitmapFont f, SpriteBatch b, Texture checked, Texture unchecked) {
        //f.draw(b,"res " + response + " ans " + currentQuestion.answer + " scr " + currentQuestion.getScore(response),100,370)

        f.draw(b, "" + ((int)(time*10))/10.0, time > 10 ? 380 : 407,810);

        if (currentQuestion instanceof QuestionMC) {
            for (int k = 0; k < currentQuestion.question.size(); k++) {
                f.draw(b, currentQuestion.question.get(k), 10, 750-k*(fontHeight + 5));
            }

            int x = 55;

            for (int k = 1; k <= 4; k++) {
                if (response * 10 == k)
                    b.draw(checked,x - 45,mcYValues[k] - 19);
                else b.draw(unchecked,x - 45,mcYValues[k] - 19);
            }

            f.draw(b,"" + ((QuestionMC)(currentQuestion)).a,x,mcYValues[1] + fontCorrection - fontHeight/2);
            f.draw(b,"" + ((QuestionMC)(currentQuestion)).b,x,mcYValues[2] + fontCorrection - fontHeight/2);
            f.draw(b,"" + ((QuestionMC)(currentQuestion)).c,x,mcYValues[3] + fontCorrection - fontHeight/2);
            f.draw(b,"" + ((QuestionMC)(currentQuestion)).d,x,mcYValues[4] + fontCorrection - fontHeight/2);
            //f.draw(b,"E: " + ((QuestionMC)(currentQuestion)).e,x,mcYValues[5] + fontCorrection - fontHeight/2);

        }
        else if (currentQuestion instanceof QuestionFR) {
            int delimIndex = 0;
            for (int k = 0; k < currentQuestion.question.size(); k++) {
                String init = currentQuestion.question.get(k), fin = "";
                for (int k2 = 0; k2 < init.length(); k2++) {
                    char ch = init.charAt(k2);
                    if (ch == '@') {
                        fin = fin + currentQuestion.delimiter.get(delimIndex++);
                    }
                    else {
                        fin += ch;
                    }
                }
                f.draw(b, fin, 60, 430-k*(fontHeight + 5));
            }

            if (response != -1) f.draw(b,"" + response,400,150);
            f.draw(b,"O",response* 600 + 100,100);
            f.draw(b,"-",45,100);
            f.draw(b,"+",755,100);
        }
    }

    public void begin() {
        response = -1;
        score = 0;
        currentQuestion = Question.randomQuestion();
        time = currentQuestion.startTime;
        results = false;
    }

    public void finish() {
        ScreenGame.question = false;
        if (score == 0) {
            GameDisplay.guy.velocity = 5;
        }
    }

	public void touchDown(int x, int y, int pointer) {

        if (results) {
            finish();
            return;
        }

        if (currentQuestion instanceof QuestionMC) {
            for (int k = 1; k <= 4; k++) {
                if (y < mcYValues[k] + mcYValues[0]/2 && y > mcYValues[k] - mcYValues[0]/2)
                    response = k/10.0f;
            }
        }
        else if (currentQuestion instanceof QuestionFR) {
            if (y < 200 && x > 95 && x < 705) {
                response = Math.round((x - 100.0f)/6.0f) / 100.0f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
            else if (y < 200 && x < 95) {
                response -= 0.01f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
            else if (y < 200 && x > 705) {
                if (response == -1) response = 1;
                response += 0.01f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
        }
	}
	
	public void touchDragged(int x, int y, int pointer) {
        if (currentQuestion instanceof QuestionFR) {
            if (y < 200 && x > 95 && x < 705) {
                response = Math.round((x - 100.0f)/6.0f) / 100.0f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
        }
	}

}
