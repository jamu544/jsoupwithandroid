package android.com.jumpco.io.jsoupinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Element;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    // This is the SUPERHERO url where the data will be fetched from
    String url = "https://superheroapi.com/ids.html";
    private Element id = null;
    private Element name = null;
    String title = null;

    public ListView listView;
    public SuperheroAdapter adapter;
    public Context context;

    public TextView textView;
    ArrayList<SuperheroPojo> idList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.htmlTitle);
        listView = findViewById(R.id.list);
        context = this;
        new ListOfSuperheros ().execute();
    }

    private class ListOfSuperheros extends AsyncTask<String,String,String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... voids) {

            Document document = null;
            try {
                 //connect to website
                 document = Jsoup.connect(url).get();
                 //get title of the website
                 title = document.title();

                 //second step
                org.jsoup.nodes.Element table = document.select("table").get(0);//select table first
                Iterator<org.jsoup.nodes.Element> row = table.select("tr").iterator();
                row.next(); //Skipping the <th>

                while (row.hasNext()) {
                    Iterator<org.jsoup.nodes.Element> ite = ((org.jsoup.nodes.Element) row.next()).select("td").iterator();
                    SuperheroPojo pojo = new SuperheroPojo();
                    pojo.id = ite.next().text();
                    pojo.name = ite.next().text();
                    idList.add(pojo);

                }
                org.jsoup.nodes.Element table2 = document.select("table").get(1);//select table first
                Iterator<org.jsoup.nodes.Element> row2 = table2.select("tr").iterator();
                row2.next(); //Skipping the <th>

                while (row2.hasNext()) {
                    Iterator<org.jsoup.nodes.Element> ite = ((org.jsoup.nodes.Element) row2.next()).select("td").iterator();
                    SuperheroPojo pojo = new SuperheroPojo();
                    pojo.id = ite.next().text();
                    pojo.name = ite.next().text();
                    idList.add(pojo);

                }
                org.jsoup.nodes.Element table3 = document.select("table").get(2);//select table first
                Iterator<org.jsoup.nodes.Element> row3 = table3.select("tr").iterator();
                row3.next(); //Skipping the <th>

                while (row3.hasNext()) {
                    Iterator<org.jsoup.nodes.Element> ite = ((org.jsoup.nodes.Element) row3.next()).select("td").iterator();
                    SuperheroPojo pojo = new SuperheroPojo();
                    pojo.id = ite.next().text();
                    pojo.name = ite.next().text();
                    idList.add(pojo);

                }

                adapter = new SuperheroAdapter(context,idList);


            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            System.out.println(" results == "+results);
            progressDialog.dismiss();
            textView.setText(title);
            listView.setAdapter(adapter);

        }
    }
}