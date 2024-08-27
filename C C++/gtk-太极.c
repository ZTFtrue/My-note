#include <gtk/gtk.h>

/* Surface to store current scribbles */
static cairo_surface_t *surface = NULL;

static void
draw_brush_arc(GtkWidget *widget)
{
  cairo_t *cr;
  GdkRGBA color;
  /* Paint to the surface, where we store our state */
  cr = cairo_create(surface);
  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_arc(cr, 101, 101, 100, 0, 2 * G_PI);
  cairo_stroke_preserve(cr);
  cairo_set_source_rgb(cr, 1, 1, 1);
  cairo_fill(cr); // 画出太极的外边框

  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_arc(cr, 101, 101, 100, 0, 1 * G_PI);
  cairo_fill(cr); // 画出一半黑

  cairo_set_source_rgb(cr, 1, 1, 1);
  cairo_arc(cr, 150, 100, 50, 0, 2 * G_PI);
  cairo_fill(cr); // 画出白圆

  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_arc(cr, 150, 100, 10, 0, 2 * G_PI);
  cairo_fill(cr); // 画出白圆上的点

  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_arc(cr, 50, 100, 50, 0, 2 * G_PI);
  cairo_fill(cr); // 画出黑圆

  cairo_set_source_rgb(cr, 1, 1, 1);
  cairo_arc(cr, 50, 100, 10, 0, 2 * G_PI);
  cairo_fill(cr); // 画出黑圆上的点
  cairo_destroy(cr);

  /* Now invalidate the affected region of the drawing area. */
  gtk_widget_queue_draw_area(widget, 27, 12, 6, 6);
}

static void
clear_surface(GtkWidget *widget)
{
  cairo_t *cr;
  cr = cairo_create(surface);
  cairo_set_source_rgb(cr, 1, 1, 1);
  cairo_paint(cr);
  cairo_destroy(cr);
  draw_brush_arc(widget);
}

/* Create a new surface of the appropriate size to store our scribbles */
static gboolean
configure_event_cb(GtkWidget *widget,
                   GdkEventConfigure *event,
                   gpointer data)
{
  if (surface)
    cairo_surface_destroy(surface);
  surface = gdk_window_create_similar_surface(gtk_widget_get_window(widget),
                                              CAIRO_CONTENT_COLOR,
                                              gtk_widget_get_allocated_width(widget),
                                              gtk_widget_get_allocated_height(widget));

  /* Initialize the surface to white */
  clear_surface(widget);
  /* We've handled the configure event, no need for further processing. */
  return TRUE;
}

/* Redraw the screen from the surface. Note that the ::draw
 * signal receives a ready-to-be-used cairo_t that is already
 * clipped to only draw the exposed areas of the widget
 */
static gboolean
draw_cb(GtkWidget *widget,
        cairo_t *cr,
        gpointer data)
{
  cairo_set_source_surface(cr, surface, 0, 0);
  cairo_paint(cr);
  draw_brush_arc(widget);
  return FALSE;
}

static void
close_window(void)
{
  if (surface)
    cairo_surface_destroy(surface);
}

static void
activate(GtkApplication *app,
         gpointer user_data)
{
  GtkWidget *window;
  GtkWidget *frame;
  GtkWidget *drawing_area;

  window = gtk_application_window_new(app);
  gtk_window_set_title(GTK_WINDOW(window), "太极图");
  gtk_window_set_default_size(GTK_WINDOW(window), 221, 221); // 设置窗口大小

  g_signal_connect(window, "destroy", G_CALLBACK(close_window), NULL); // 监听窗口

  gtk_container_set_border_width(GTK_CONTAINER(window), 8);

  frame = gtk_frame_new(NULL);
  gtk_frame_set_shadow_type(GTK_FRAME(frame), GTK_SHADOW_IN);
  gtk_container_add(GTK_CONTAINER(window), frame);

  drawing_area = gtk_drawing_area_new();
  /* set a minimum size */
  gtk_widget_set_size_request(drawing_area, 100, 100);
  gtk_container_add(GTK_CONTAINER(frame), drawing_area);
  /* Signals used to handle the backing surface */
  g_signal_connect(drawing_area, "draw",
                   G_CALLBACK(draw_cb), NULL);
  g_signal_connect(drawing_area, "configure-event",
                   G_CALLBACK(configure_event_cb), NULL);
  gtk_widget_show_all(window);
}

int main(int argc,
         char **argv)
{
  GtkApplication *app;
  int status;

  app = gtk_application_new("org.gtk.example", G_APPLICATION_FLAGS_NONE);
  g_signal_connect(app, "activate", G_CALLBACK(activate), NULL);
  status = g_application_run(G_APPLICATION(app), argc, argv);
  g_object_unref(app);

  return status;
}
/**
 * 编译方法 
 * gcc `pkg-config --cflags gtk+-3.0` -o example-4 example-4.c `pkg-config --libs gtk+-3.0`
 * 参考 https://developer.gnome.org/gtk3/stable/ch01s05.html
**/
/**
 * 
                              _ooOoo_
                             o8888888o
                             88" . "88
                             (| -_- |)
                             O\  =  /O
                          ____/`---'\____
                        .'  \\|     |//  `.
                       /  \\|||  :  |||//  \
                      /  _||||| -:- |||||-  \
                      |   | \\\  -  /// |   |
                      | \_|  ''\---/''  |   |
                      \  .-\__  `-`  ___/-. /
                    ___`. .'  /--.--\  `. . __
                 ."" '<  `.___\_<|>_/___.'  >'"".
                | | :  `- \`.;`\ _ /`;.`/ - ` : | |
                \  \ `-.   \_ __\ /__ _/   .-` /  /
           ======`-.____`-.___\_____/___.-`____.-'======
                              `=---='
           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                      佛祖保佑        永无BUG
             佛曰:
                    写字楼里写字间，写字间里程序员；
                    程序人员写程序，又拿程序换酒钱. 
                    酒醒只在网上坐，酒醉还来网下眠；
                    酒醉酒醒日复日，网上网下年复年. 
                    但愿老死电脑间，不愿鞠躬老板前；
                    奔驰宝马贵者趣，公交自行程序员. 
                    别人笑我忒疯癫，我笑自己命太贱；
                    不见满街漂亮妹，哪个归得程序员？
*/
