Cartfragment.java的onviewcreated的onclick:

      mainActivity.updateHistoryRecord();

MainActivity.java:

      public static ArrayList<String> updateHistoryRecord(){
            ArrayList<String> records = client.getHistoryCart(user);
            if(!records.isEmpty()){
                return records;
            }else{
                return null;
            }
        }

personalfragment.java的onviewcreated:

      if(!MainActivity.updateHistoryRecord().isEmpty()){
                  RecyclerView historyView = getView().findViewById(R.id.historyRecord);
                  ArrayList<cart_item> sitems = new ArrayList<>();
                  for (String s: MainActivity.updateHistoryRecord() ) {
                      sitems.add(new cart_item(s));
                  }
                  historyView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                  historyView.setAdapter(new cartItem_adapter(this.getContext().getApplicationContext(),sitems, MainActivity.updateHistoryRecord()));
              }
        
fragment_personal.xml的104行:

      android:id="@+id/historyRecord"

        
