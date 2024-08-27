from pdf2image import convert_from_path
from PIL import Image
# PDF 转单个图片
def pdf_to_single_image(pdf_path, output_path):
    # Convert PDF to images
    images = convert_from_path(pdf_path)

    # Combine images into one
    widths, heights = zip(*(i.size for i in images))
    max_width = max(widths)
    total_height = sum(heights)

    combined_image = Image.new("RGB", (max_width, total_height))

    y_offset = 0
    for image in images:
        combined_image.paste(image, (0, y_offset))
        y_offset += image.size[1]

    # Save combined image
    combined_image.save(output_path)

# Example usage
pdf_file = "20240408.pdf"  # Replace "example.pdf" with your PDF file path
output_image = "output_image.jpg"  # Path where the single image will be saved

pdf_to_single_image(pdf_file, output_image)
