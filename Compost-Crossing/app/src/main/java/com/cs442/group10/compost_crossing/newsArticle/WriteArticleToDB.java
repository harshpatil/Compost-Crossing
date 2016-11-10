package com.cs442.group10.compost_crossing.newsArticle;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by HarshPatil on 10/31/16.
 */
public class WriteArticleToDB {

    public void writeToDb() throws Exception {


//        String csvFile = "/Users/HarshPatil/Desktop/input.csv";
//        BufferedReader bufferedReader = null;
//        String line = "";
//        String cvsSplitBy = ",";
//        int today = 9;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(csvFile));
//            while ((line = bufferedReader.readLine()) != null) {
//
//                // use comma as separator
//                String[] field = line.split(cvsSplitBy);
//                String title = field[0];
//                String body = field[1];
    String title = "Straw bale gardening and other tips to build the best soil for your raised beds.";
    String body = "Adding some raised beds to your garden this year? Great idea. I’ve seen it said that raised beds produce about four times the amount of produce as do row crops. Plants seem more vigorous there in early season, probably because the soil in a raised bed warms faster than that in the garden patch. As gardeners, we love early season growth.\nNone of this is true, of course, if the soil in your raised bed isn’t at its best. And that’s the great things about raised beds. You can dig them out and fill them as you like. Think of them as a controlled experiment in which you’re looking for just the right, airy mix of organic materials — including beneficial microbes and other living things — and naturally occurring nutrients like nitrogen and minerals. The easy way is to just buy the best top soil and compost, in bags or not, and fill up the bed’s box. If you have your own compost, or can get reliable, organic compost — we were lucky to get it from a local, organic dairy goat farm — it’s worth making your own soil blend. That way, you’ll be able to fine-tune it for particular crops. Growing tomatoes? Make your soil slightly acidic, just the way they like it. Growing greens? You’ll want to keep the nitrogen low until you have germination.\nIf you’re using compost, make sure that it’s completely finished. If you’re adding manures of any kind, make sure they’re completely composted and are no longer “hot.” Mix in other materials, like peat or perlite if you’re looking for good drainage, or sand, which root vegetables like. The easiest way to make sure compost is garden-ready is to spread it in the fall, leaving it on the surface to finish through the freeze and thaw cycles of winter.\nIf you didn’t spread compost in the fall or just don’t have any to spare, you can make it on the spot and grow in it as you do. We’ve stuck a bale or two of straw in raised beds in the fall and were left with good results when we pulled the remnants off in the spring.\nNot only does the bale smother any weeds that might try to poke up early, it conditions the soil beneath where it sits. We even know someone that placed a bale right on the sod where he put his box in August and finished up with top soil when it came time to plant next spring. Personally, I’d go to the trouble to dig out all the sod ahead of putting down bales. But that’s just me. If you’re putting in new raised beds this spring, why not put your first planting right in the straw bales? The craft of straw bale gardening has grown in popularity and for good reason. Some gardeners skip the box all together and just grow in the bale.\nOnce the hay bale growing is over, your raised box will be left with good-quality left behind from the composted straw. You can hasten the process by adding some compost or top soil which you’ll probably do as part of sticking plants in the bale. Either way, the soil inside your raised bed will benefit.\n\nBy E. Vinje\n";

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("articlesList");
                News news = new News();
                news.setTitle(title);
                news.setBody(body);
//                today++;
//                String day = String.valueOf(today);
//                Log.i("CSV","CSV");
//                myRef.child("news_"+ day +"112016").setValue(news);
                myRef.child("news_13112016").setValue(news);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                        Log.w("DataBase", "Failed to read value.", error.toException());
                    }
                });
            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
