package uk.co.methodical;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ImageCreator {

	public static BufferedImage CreateImage(Touch touch) {

		
		int practice_bell = 2;
		int number_of_hunts = touch.getLastLeadEnd().getMethod().getNumber_of_hunts();
		if (number_of_hunts + 1 < touch.getNumber_of_bells()) {
			practice_bell = number_of_hunts + 1;
		}
		
		int font_size = 12;
		int header_font_size = 14;
		int x_position = 10;
		int y_position = 2 * header_font_size;
		double line_spacing = 1.5 * font_size;
		double horizontal_spacing = 2 * font_size;

		long image_height = Math.round(touch.getLength() * line_spacing + 2
				* y_position);
		long image_width = Math.round(touch.getNumber_of_bells()
				* horizontal_spacing + 2 * x_position + 3 * font_size);

		boolean first_lead_head = true;

		BufferedImage img = new BufferedImage((int) image_width,
				(int) image_height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();

		g2.setBackground(Color.WHITE);
		// g2.setPaint(Color.BLUE);
		g2.setPaint(Color.BLACK);
		BasicStroke line_type = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		BasicStroke circle_line_type = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		g2.setStroke(line_type);
		// g2.draw(new Rectangle(10, 15, 50, 30));

		Font string_font = new Font(g2.getFont().getFontName(), Font.PLAIN,
				font_size);
		Font header_font = new Font(g2.getFont().getFontName(), Font.PLAIN,
				header_font_size);
		
		g2.setFont(header_font);
		g2.drawString(touch.getLastLeadEnd().getMethod().getName(), x_position,
				header_font_size);

		g2.setFont(string_font);
		
		double treble_line_from_x = 0;
		double treble_line_from_y = 0;
		double line_from_x = 0;
		double line_from_y = 0;
		
		for (Iterator<LeadEnd> lead_end_iterator = touch.getLead_ends()
				.iterator(); lead_end_iterator.hasNext();) {
			
			LeadEnd le = lead_end_iterator.next();
			boolean is_lead_head = true;
			
			for (Iterator<int[]> row_iterator = le.getRows().iterator(); row_iterator
					.hasNext();) {
				
				int[] row = row_iterator.next();	
				
				boolean is_lead_end = !row_iterator.hasNext();
				Integer place_bell_start = 0;
				int pen_x_position = 0;
				
				for (int position_no = 0; position_no < row.length; ++position_no) {
					
					pen_x_position = x_position + font_size * position_no
							* 2;
					g2.drawString((new Integer(row[position_no])).toString(),
							pen_x_position, y_position);

					// Draw the blue line
					if (row[position_no] == practice_bell) {
						double line_to_x = pen_x_position + 0.3 * font_size;
						double line_to_y = y_position - 0.3 * font_size;
						
						if (!first_lead_head) {
							g2.setPaint(Color.BLUE);
							g2.draw(new Line2D.Double(line_from_x, line_from_y,
									line_to_x, line_to_y));
							g2.setPaint(Color.BLACK);
						}
						line_from_x = line_to_x;
						line_from_y = line_to_y;
						
						if (is_lead_head) {
							place_bell_start = position_no+1;
							
							//is_lead_head = false;
						}
						
					// Draw the treble line	
					} else if (row[position_no] == 1) {
						double treble_line_to_x = pen_x_position + 0.3 * font_size;
						double treble_line_to_y = y_position - 0.3 * font_size;
						
						if (!first_lead_head) {
							g2.setPaint(Color.RED);
							g2.draw(new Line2D.Double(treble_line_from_x, treble_line_from_y,
									treble_line_to_x, treble_line_to_y));
							g2.setPaint(Color.BLACK);
						}
						treble_line_from_x = treble_line_to_x;
						treble_line_from_y = treble_line_to_y;
					}
				}
				
				if (is_lead_end) {
					pen_x_position = pen_x_position + font_size * 3;
					g2.drawString(place_bell_start.toString(),
							pen_x_position, y_position);
		
					g2.setStroke(circle_line_type);
					g2.drawOval(pen_x_position-(int)(0.75*font_size), y_position-(int)(1.5*font_size), 2*font_size, 2*font_size);
					g2.setStroke(line_type);
					
					/*;(new Circle()
							Line2D.Double(treble_line_from_x, treble_line_from_y,
							treble_line_to_x, treble_line_to_y));*/
					
					is_lead_head = false;
				}
				
				y_position += line_spacing;
			}

			if (first_lead_head) {
				first_lead_head = false;
			} else {
				g2.draw(new Line2D.Double(x_position, y_position - 2
						* line_spacing + 0.5*font_size, x_position
						+ (touch.getNumber_of_bells() - 0.5)
						* horizontal_spacing, y_position - 2 * line_spacing + 0.5*font_size));
			}

		}

		// g2.draw(new Line2D.Double(10+2.5*fontSize, 70-0.5*fontSize, 200,
		// 100));

		/*
		 * Font stringFont = new Font(g2.getFont().getFontName(),Font.PLAIN,
		 * fontSize); g2.setFont(stringFont); g2.setPaint(Color.BLACK); for
		 * (Integer i = 1; i <= 20; ++i) { g2.drawString(i.toString(),
		 * 10+fontSize*i*2, 70); }
		 */

		return img;

	}
}
