package com.cs442.group10.compost_crossing.newsArticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs442.group10.compost_crossing.MainActivity;
import com.cs442.group10.compost_crossing.R;

/**
 * Created by HarshPatil on 10/30/16.
 */
public class Article extends AppCompatActivity {

    TextView articleTitle;
    TextView articleBody;
    Button goToHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_article);

        String title = "Keep tomato plants out of the landfill? Or keep tomato diseases out of your garden? It’s not always simple.";
        String body = "Your friendly Planet Natural blogger never thought much about composting tomatoes until a reader sent in a question about it. We’ve always done it if, at the end of the season, the plants had been healthy and showed no signs of blight, wilt or insect infestations. Thing is, by the end of the season, especially in damper climates, tomatoes often show signs of all these things.\n" +
                "\n" +
                "Add to that the fact that the dry, stringy vines don’t break down quickly and tend to get tangled up with everything else in the pile, and the result is this: I don’t end up composting tomato plants very often.\n" +
                "\n" +
                "Little did I know until I started digging around into the matter of tomato composting just how controversial the subject was. More than a couple garden forums host discussions in which people say they would never compost tomato vines, usually because of the disease that might be spread, countered by people who say it’s a shame to send tomato vines to the landfill rather than returning them, in composted form, back to the garden.Some responders addressed the question of recycling the tomato fruits themselves into the compost pile. Yes, your pile can handle probably the acid unless you’re composting crates and crates of tomatoes. The only problem — if it is a problem — is your compost pile springing forth with volunteer tomatoes when weather starts to warm in the spring.\n" +
                "\n" +
                "We’ve never much been bothered by volunteer tomato or cucumber or potato plants springing up out of our compost heap. We usually — except, sometimes, for the potatoes — turn the young plants back in with the rest of the decomposing material. Many of today’s tomatoes are hybrids of the sort that don’t breed true on second go-around. They probably won’t bear fruit no matter how well nourished they are while growing out of your compost.\n" +
                "\n" +
                "But even recycling the fruit can be risky. I wouldn’t risk throwing tomatoes that show sings of anthracnose, those dark, wet spots the turn up on fruits and can grow up to a half-inch around as well as penetrating into the tomato’s flesh. I wouldn’t want to risk introducing the fungus that causes it to compost that will later be spread around the garden.\n" +
                "\n" +
                "That’s the deciding factor as far as I’m concerned: risk. Anything that might introduce disease or pests into my compost pile (and then into my garden) goes into the trash. It’s argued that compost piles that get hot enough will destroy all pests, diseases and other pathogens as well as seeds. My heaps, no matter how much I turn them rarely do.\n" +
                "\n" +
                "Sure, if you live in an area that allows open burning (my guess is most of us do not), do that to your diseased plants at the end of the year. Otherwise, don’t take the risk. Put them in the trash.We frequently make the argument that composting reduces our contribution to local landfills. So to throw some plants out with the trash seems a bit hypocritical. On the other hand, if we do spread disease into our compost and then into our gardens, we end up with even more plants that should go into the landfill.\n" +
                "\n" +
                "Nothing is simple. Some plant disease, like curly top virus which twist leaves out of shape and makes plants take on a yellow hue, break down quickly in compost, hot or not, or so we’re told (the disease is most often transmitted by whiteflies and other insects). Odds are, if you’re in an area that suffers from curly top, you’re going to get it no matter what is in your compost.\n" +
                "\n" +
                "Then again, what if it’s not curly top but some other, more durable virus?\n" +
                "\n" +
                "So the answer is no, generally we don’t compost our tomatoes, unless they’ve been extremely healthy (we just put up with the vines). Conditions have to be right. It seems that most gardeners will make the decision for themselves, depending on their growing conditions and personal risk aversion.";

        articleTitle = (TextView) findViewById(R.id.articleTitle);
        articleBody = (TextView) findViewById(R.id.articleBody);

        articleTitle.setText(title);
        articleBody.setText(body);

        goToHomePageButton = (Button)findViewById(R.id.backButtonArticlePage);
        goToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingGoToHomePageButton();
            }
        });

    }

    public void onClickingGoToHomePageButton(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}
