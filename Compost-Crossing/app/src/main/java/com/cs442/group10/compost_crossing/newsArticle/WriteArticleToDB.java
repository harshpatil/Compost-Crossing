package com.cs442.group10.compost_crossing.newsArticle;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by HarshPatil on 10/31/16.
 */
public class WriteArticleToDB {

    public static void main(String args[]){

        String title = "How to compost your kitchen scraps and other green waste over the winter months.";
        String body = "Winter traditionally puts a chill on composting. Short, cold days allow conditions inside your compost piles to cool down to almost nothing. The supply of compostable materials from your landscape falls off once the leaves are raked and the freeze is on.\n\nBut composting doesn’t have to come to a full-stop during the cold months. Here’s how to care for your established, outdoor compost pile during winter, plus tips to start new ones, even in January. Like a lot of activities, composting can come indoors during the frozen months with Bokashi buckets and vermicomposting.\n\nFirst, know what you’re doing. A solid background in composting that includes understanding of green and brown material ratios, the best moisture conditions for making compost and other basic knowledge will go a long way in keeping your compost active during the winter. Collect all the leaves you can. Winter heaps need more brown material to stay active. Yet most of what you’ll be bringing out from the kitchen will be green.\nBulk up the inside of the pile when turning the heaps in the fall. Leave as much air space inside as possible. This puff factor, like goose down in a jacket, helps with insulation. It also provides for oxidation — the decaying process — of the kitchen scraps you’re adding.\nInsulate your pile with fall leaves. They’ll help keep in the heat generated at the center of the pile. Straw makes a good cover if leaves are in short supply. To keep leaves in place, use a tarp or, like my grandfather and Stu Campbell, author of Let It Rot: The Gardener’s Guide to Composting did, cover the heaps with a burlap or old duck canvas cover. The fabric holds in heat and moisture and stays breathable, even when soaked.\nAs the University of Wisconsin-Extension suggests in its piece on winter composting, leave a hole in the leaf cover at the top of the heap. That’s where you’ll pour kitchen scraps into the pile. Pull leaves from around the sides of the heap and add to the hole after adding green waste to make for good green-brown layering.\nBuild a wind block of straw bales around your pile.\nCovering growing beds — or places where new beds will be sited — with sheet compost, famously known as the “lasagna” method is a horizontal, on-the-spot method of building better soil right in your garden. After the ground freezes, you can tuck your kitchen scraps under the cover. This makes for good green (scraps) and brown (straw) balance.\nHave more leaves than your piles and tumbler will accept? Bag them along with some green material — kitchen scraps or fresh mown grass — and let them overwinter, preferably in the sun. Black trash bags are best. They develop more heat in the sunlight. In the spring you can add the composting leaves from the bags into your piles or directly in your garden, depending on their condition. Protect your piles with walls or some kind of break on the east, north and west sides. Keep the south side open to the sun for maximum warming. Site compost tumblers to take best advantage of the sun and protect them from the wind.\nScoop out soil where the pile will be located to a depth of one or two feet. Being partly submerged in the earth helps keep the pile insulated and active.\nYou’ll have to shovel a path to your piles in regions that see major snow accumulations. Keep your piles close and convenient to your back door, but not so close that they’ll be an eyesore come summer.\nTrenching — Storing your green waste in shallow trenches dug directly in your garden gets your scraps pile-ready come spring. This method gets the composting process started early especially in areas that experience frequent freeze and thaw cycles and may even yield finished compost if the ingredients are chopped finely enough. In cold regions, six to ten weeks in the ground may not be enough to have finished waste. When the daily temperatures begin to rise in March or so, dig them up and transfer to your piles/heaps or tumbler. You’ll have kept the microbes in your garden soil active and well-fed.Low humidity and winter winds, both problems here in the mountain west, can dry out your pile. Water it sparingly on a day when the moisture won’t immediately freeze as needed.\nAdd only green kitchen scraps to your pile during winter and use large quantities — you can hold your scrapes in pails or buckets — when you do. Adding finished chicken or steer manure, blood meal, alfalfa pellets or other high nitrogen amendments will help keep temperatures and microbe activity up.\nAs winter turns to spring and moisture increases, compost heaps may turn slimy and start to smell. This is a good time to add brown material — leaves if you have them left from fall — or dry sawdust.\nCompost tumblers: Turn your compost as frequently as possible. Add as much green material — kitchen scraps and the like — to keep whatever activity there is existent. In between, drape your compost tumbler with a well-anchored tarp that will insulate and serve as a wind-break. Locate your tumbler near landscape or buildings that block prevailing winds.\nIf what’s inside your tumbler freezes solid, don’t attempt to turn it. There’s no need. It’s axles and pivots are probably too frozen to budge. And if you do succeed, sudden shifting of the heavy, icy block inside can damage the tumbler and, worse, the person doing the tumbling. Wait for the thaw.\nMake sure scraps are finely chopped. Don’t use rinds.\"\n\nBy E. Vinje\n";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("articlesList");
        News news = new News();
        news.setTitle(title);
        news.setBody(body);
        myRef.child("news_02112016").setValue(news);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.i("DataBase", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("DataBase", "Failed to read value.", error.toException());
            }
        });
    }
}
