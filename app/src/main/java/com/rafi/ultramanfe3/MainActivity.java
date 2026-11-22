package com.rafi.ultramanfe3;
import android.graphics.drawable.GradientDrawable;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
    static final int PINK = Color.rgb(235, 25, 105);
    static final int DARK = Color.rgb(35, 35, 42);
    static final int MUTED = Color.rgb(112, 116, 126);
    static final int BG = Color.rgb(247, 247, 250);
    String isoUri = "";
    String isoName = "Belum ada ISO dipilih";
    String profile = "Kecil";
    LinearLayout root, content, sidebar;
    TextView status;
    boolean inGame = false;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(24,24,28));
        getWindow().setNavigationBarColor(Color.rgb(24,24,28));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getPreferences(0);
        isoUri = getPreferences(0).getString("iso_uri", "");
        isoName = getPreferences(0).getString("iso_name", "Belum ada ISO dipilih");
        profile = getPreferences(0).getString("profile", "Kecil");
        copyBundledSave();
        showHome();
    }

    int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + .5f); }
    TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        t.setGravity(Gravity.CENTER_VERTICAL); return t;
    }
    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(radius)); return d;
    }
    LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(1); return l; }
    LinearLayout row() { LinearLayout l = new LinearLayout(this); l.setOrientation(0); l.setGravity(Gravity.CENTER_VERTICAL); return l; }
    void pad(View v, int a) { v.setPadding(dp(a),dp(a),dp(a),dp(a)); }
    TextView button(String label, boolean primary, Runnable action) {
        TextView b = text(label, 15, primary ? Color.WHITE : DARK, true);
        b.setGravity(Gravity.CENTER); b.setPadding(dp(18),dp(12),dp(18),dp(12));
        b.setBackground(shape(primary ? PINK : Color.rgb(238,239,243), 18));
        b.setOnClickListener(v -> action.run()); return b;
    }
    void base(String title) {
        root = column(); root.setBackgroundColor(BG); setContentView(root);
        LinearLayout top = row(); top.setBackgroundColor(PINK); top.setPadding(dp(18),dp(10),dp(18),dp(10));
        TextView brand = text("ULTRAMAN FE3  /  COOKER", 18, Color.WHITE, true);
        top.addView(brand, new LinearLayout.LayoutParams(0, dp(48), 1));
        TextView menu = text("☰", 28, Color.WHITE, true); menu.setGravity(Gravity.CENTER);
        menu.setOnClickListener(v -> showSidebarDialog()); top.addView(menu, new LinearLayout.LayoutParams(dp(54),dp(48)));
        root.addView(top);
        LinearLayout body = row(); root.addView(body, new LinearLayout.LayoutParams(-1,0,1));
        sidebar = column(); sidebar.setPadding(dp(10),dp(12),dp(10),dp(12));
        sidebar.setBackgroundColor(Color.WHITE);
        body.addView(sidebar, new LinearLayout.LayoutParams(dp(190),-1));
        addSide("▶  Play Game", () -> showHome());
        addSide("⚙  Pengaturan", () -> showSettings());
        addSide("▤  Grafik", () -> showGraphics());
        content = column(); content.setPadding(dp(18),dp(14),dp(18),dp(14));
        ScrollView scroll = new ScrollView(this); scroll.setFillViewport(true); scroll.addView(content);
        body.addView(scroll, new LinearLayout.LayoutParams(0,-1,1));
        TextView foot = text("Ultraman FE3 Cooker • Build 0.1.0 • PS2 engine belum terintegrasi", 10, MUTED, false);
        foot.setGravity(Gravity.CENTER); root.addView(foot,new LinearLayout.LayoutParams(-1,dp(24)));
    }
    void addSide(String label, Runnable r) {
        TextView t = text(label,14,DARK,true); t.setPadding(dp(10),dp(13),dp(6),dp(13));
        t.setBackground(shape(Color.rgb(247,247,250),14));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,dp(48)); lp.bottomMargin=dp(8);
        sidebar.addView(t,lp); t.setOnClickListener(v -> r.run());
    }
    void showSidebarDialog() {
        new AlertDialog.Builder(this).setTitle("Menu")
          .setItems(new String[]{"Play Game","Pengaturan","Grafik"},(d,w)->{
              if(w==0)showHome(); else if(w==1)showSettings(); else showGraphics();
          }).show();
    }
    void heading(String s) { TextView t=text(s,23,DARK,true); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(48)); content.addView(t,p); }
    void showHome() {
        inGame=false; base("Home"); heading("Ultraman Fighting Evolution 3");
        TextView desc=text("Japan • Taikenban • PS2 • Data game disediakan oleh pengguna",13,MUTED,false);
        content.addView(desc);
        TextView card=text("ULTRAMAN\nFIGHTING EVOLUTION 3\n\nLauncher khusus satu game PS2",20,Color.WHITE,true);
        card.setGravity(Gravity.CENTER); card.setBackground(shape(Color.rgb(55,60,75),22));
        LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,dp(135)); cp.setMargins(0,dp(12),0,dp(12)); content.addView(card,cp);
        TextView iso=text("File game: "+isoName,14,DARK,true); pad(iso,14); iso.setBackground(shape(Color.WHITE,16));
        content.addView(iso,new LinearLayout.LayoutParams(-1,dp(54)));
        LinearLayout actions=row(); LinearLayout.LayoutParams ap=new LinearLayout.LayoutParams(-1,dp(52)); ap.topMargin=dp(12);
        actions.addView(button("＋  Import ISO",true,()->pickIso()),new LinearLayout.LayoutParams(0,-1,1));
        TextView gap=text("",10,DARK,false); actions.addView(gap,new LinearLayout.LayoutParams(dp(10),1));
        actions.addView(button("▶  Play Game",false,()->startGame()),new LinearLayout.LayoutParams(0,-1,1));
        content.addView(actions,ap);
        TextView note=text("Save data bawaan: sudah dikemas dalam APK sebagai data awal. Penerapan ke memory card emulator menunggu integrasi engine.",12,MUTED,false);
        note.setPadding(0,dp(16),0,0); content.addView(note);
        TextView engine=text("STATUS ENGINE: BELUM TERINTEGRASI\nTombol Play saat ini membuka layar kontrol uji, bukan menjalankan ISO.",12,Color.rgb(160,70,20),true);
        engine.setPadding(dp(12),dp(12),dp(12),dp(12)); engine.setBackground(shape(Color.rgb(255,241,225),14));
        LinearLayout.LayoutParams ep=new LinearLayout.LayoutParams(-1,-2); ep.topMargin=dp(12); content.addView(engine,ep);
    }
    void pickIso() {
        Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT); i.setType("*/*");
        i.putExtra(Intent.EXTRA_MIME_TYPES,new String[]{"application/octet-stream","application/x-iso9660-image"});
        i.addCategory(Intent.CATEGORY_OPENABLE); startActivityForResult(i,42);
    }
    @Override protected void onActivityResult(int req,int res,Intent data) {
        super.onActivityResult(req,res,data);
        if(req==42 && res==RESULT_OK && data!=null && data.getData()!=null) {
            Uri u=data.getData(); String n=u.getLastPathSegment();
            try { CursorName c=displayName(u); if(c.name!=null)n=c.name; } catch(Exception ignored){}
            if(n==null)n="Selected game file";
            String low=n.toLowerCase(Locale.ROOT);
            if(!(low.endsWith(".iso")||low.endsWith(".bin")||low.endsWith(".chd"))) {
                toast("Pilih file ISO/BIN/CHD."); return;
            }
            getContentResolver().takePersistableUriPermission(u,data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
            isoUri=u.toString(); isoName=n;
            getPreferences(0).edit().putString("iso_uri",isoUri).putString("iso_name",isoName).apply();
            toast("File dipilih: "+n); showHome();
        }
    }
    static class CursorName { String name; }
    CursorName displayName(Uri u) {
        CursorName c=new CursorName();
        try(android.database.Cursor cur=getContentResolver().query(u,null,null,null,null)){
            if(cur!=null && cur.moveToFirst()){int x=cur.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);if(x>=0)c.name=cur.getString(x);}
        } catch(Exception ignored){} return c;
    }
    void startGame() {
        if(isoUri.isEmpty()){toast("Import ISO game terlebih dahulu.");return;}
        new AlertDialog.Builder(this).setTitle("Engine PS2 belum tersedia")
          .setMessage("ISO sudah dipilih, tetapi engine PS2 belum disertakan pada build ini. Layar kontrol di bawah hanya untuk uji desain, belum mengirim input ke game.")
          .setNegativeButton("Kembali",null).setPositiveButton("Lihat kontrol", (d,w)->showTouchTest()).show();
    }
    void showTouchTest() {
        inGame=true; getWindow().getDecorView().setSystemUiVisibility(5894|1024|512|4096);
        FrameLayout frame=new FrameLayout(this); frame.setBackgroundColor(Color.rgb(25,28,35)); setContentView(frame);
        TextView title=text("ULTRAMAN FE3  •  TOUCH CONTROL PREVIEW",13,Color.WHITE,true);
        title.setPadding(dp(14),dp(8),dp(14),dp(8)); frame.addView(title);
        TextView arena=text("GAME SCREEN PLACEHOLDER\nPS2 engine integration pending",18,Color.WHITE,true);
        arena.setGravity(Gravity.CENTER); frame.addView(arena,new FrameLayout.LayoutParams(-1,-1));
        TouchOverlay overlay=new TouchOverlay(this); frame.addView(overlay,new FrameLayout.LayoutParams(-1,-1));
        TextView exit=button("✕",false,()->showHome()); FrameLayout.LayoutParams ep=new FrameLayout.LayoutParams(dp(48),dp(44),Gravity.RIGHT|Gravity.TOP); ep.setMargins(0,dp(8),dp(10),0); frame.addView(exit,ep);
    }
    class TouchOverlay extends View {
        Paint p=new Paint(3); float downX,downY;
        String[] labels={"↑","←","↓","→","□","△","×","○","L1","L2","R1","R2"};
        TouchOverlay(Context c){super(c);setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
        void circle(Canvas c,float x,float y,float r,String s) {
            p.setColor(0x663C4658); p.setStyle(Paint.Style.FILL); c.drawCircle(x,y,r,p);
            p.setColor(0xBFFFFFFF); p.setStrokeWidth(dp(2)); p.setStyle(Paint.Style.STROKE); c.drawCircle(x,y,r,p);
            p.setStyle(Paint.Style.FILL); p.setTextAlign(Paint.Align.CENTER); p.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));
            p.setTextSize(s.length()>1?dp(12):dp(24)); c.drawText(s,x,y+dp(8),p);
        }
        protected void onDraw(Canvas c) {
            super.onDraw(c); float w=getWidth(),h=getHeight(); float r=Math.min(dp(27),h*.075f);
            float cx=dp(72), cy=h*.70f;
            circle(c,cx,cy-r*1.8f,r,"↑"); circle(c,cx-r*1.8f,cy,r,"←");
            circle(c,cx,cy,r,"↓"); circle(c,cx+r*1.8f,cy,r,"→");
            float ax=w-dp(75), ay=h*.70f;
            circle(c,ax-r*1.8f,ay-r*1.2f,r,"□"); circle(c,ax,ay-r*1.8f,r,"△");
            circle(c,ax-r*1.8f,ay+r*.7f,r,"×"); circle(c,ax,ay+r*1.5f,r,"○");
            circle(c,w*.34f,dp(48),dp(20),"L1"); circle(c,w*.45f,dp(48),dp(20),"L2");
            circle(c,w*.55f,dp(48),dp(20),"R2"); circle(c,w*.66f,dp(48),dp(20),"R1");
            p.setColor(0xAAFFFFFF); p.setTextSize(dp(10)); p.setTextAlign(Paint.Align.CENTER);
            c.drawText("TOUCH LAYOUT PREVIEW — NOT CONNECTED TO GAME ENGINE",w/2,h-dp(12),p);
        }
        public boolean onTouchEvent(android.view.MotionEvent e) { return true; }
    }
    void showSettings() {
        base("Settings"); heading("Pengaturan");
        TextView a=text("Orientasi: Landscape 16:9",15,DARK,true); content.addView(a);
        TextView b=text("Kontrol: Touchscreen overlay saat gameplay",14,MUTED,false); content.addView(b);
        TextView c=text("Save data bawaan tersimpan di paket aplikasi. Penggunaan oleh engine belum aktif.",13,MUTED,false);
        c.setPadding(0,dp(12),0,dp(12)); content.addView(c);
        content.addView(button("Kembali ke Home",true,()->showHome()));
    }
    void showGraphics() {
        base("Graphics"); heading("Pengaturan Grafik");
        TextView intro=text("Pilih profil sesuai kemampuan perangkat. Profil adalah preset awal, bukan jaminan bebas lag.",13,MUTED,false);
        content.addView(intro);
        String[] names={"Kecil","Menengah","Tinggi"};
        String[] details={"RAM 2 GB ke bawah • resolusi rendah, efek minimal","RAM 4 GB ke atas • kualitas seimbang","RAM 8 GB ke atas • kualitas tinggi"};
        for(int i=0;i<names.length;i++){
            final String n=names[i]; LinearLayout box=column(); box.setPadding(dp(12),dp(8),dp(12),dp(8)); box.setBackground(shape(Color.WHITE,14));
            TextView t=text(n+"  "+(profile.equals(n)?"✓ AKTIF":""),16,DARK,true); box.addView(t);
            TextView d=text(details[i],12,MUTED,false); box.addView(d);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.topMargin=dp(8); content.addView(box,lp);
            box.setOnClickListener(v->{profile=n;getPreferences(0).edit().putString("profile",profile).apply();showGraphics();});
        }
        content.addView(button("Kembali",true,()->showHome()));
    }
    void copyBundledSave() {
        try {
            File dest=new File(getFilesDir(),"bundled-save-data"); if(dest.exists())return;
            copyAssetDir("save-data",dest);
        } catch(Exception e){ android.util.Log.e("FE3","Save data copy failed",e); }
    }
    void copyAssetDir(String path,File out)throws IOException {
        String[] children=getAssets().list(path);
        if(children==null||children.length==0){out.getParentFile().mkdirs();try(InputStream in=getAssets().open(path);FileOutputStream o=new FileOutputStream(out)){byte[] b=new byte[8192];int n;while((n=in.read(b))>0)o.write(b,0,n);}return;}
        out.mkdirs(); for(String child:children)copyAssetDir(path+"/"+child,new File(out,child));
    }
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
    @Override public void onBackPressed(){ if(inGame){inGame=false;showHome();} else showHome(); }
}
