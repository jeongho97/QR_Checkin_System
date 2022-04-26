import socket 
import threading
import time
import sys

import RPi.GPIO as GPIO

import Adafruit_GPIO.SPI as SPI
import Adafruit_SSD1306

from PIL import Image
from PIL import ImageDraw
from PIL import ImageFont

import subprocess
global secrete,count
secrete = ""
count=0

def send():
    global count
    while 1:
        
        #print(9)
        s_data = "setting pwd in server "+str(count+1)
        conn.send(s_data.encode("UTF-8"))

        time.sleep(2)

    conn.close()

 

        

def receive():
    global secrete,count
    while 1:
        r_data = conn.recv(1024)
        r_data = r_data.decode("utf8").strip()
        secrete=r_data
        count=count+1
        print(r_data)
        print(count)
       #f= open('data.txt','w')
        #f.write(data)
        #f.close()
        #QRdoor(r_data)
    conn.close()



#HOST = "192.168.43.235"   # wifi address 
HOST = "192.168.0.7"   # wifi address
PORT = 8000

print("Socket Waiting !")

 

 

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((HOST,PORT))

s.listen(1)

 

conn, addr = s.accept()

print("Connected by" , addr)

receiver = threading.Thread(target = receive)
sender = threading.Thread(target = send)
#door=threading.Thread(target = QRdoor)

#print(1)
receiver.start()
sender.start()
#print(2)

#print(3)
#door.start()

while(True):
    if secrete != ""and count==3:
        RST = None    
        DC = 23
        SPI_PORT = 0
        SPI_DEVICE = 0

        disp = Adafruit_SSD1306.SSD1306_128_32(rst=RST)

        disp.begin()

        disp.clear()
        disp.display()

        width = disp.width
        height = disp.height
        image = Image.new('1', (width, height))

        draw = ImageDraw.Draw(image)
        draw.rectangle((0,0,width,height), outline=0, fill=0)

        top = 10

        count=0
        font = ImageFont.load_default()

        #SERVO=18
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(24,GPIO.OUT)
        #GPIO.setmode(GPIO.BCM)
        #GPIO.setup(SERVO,GPIO.OUT)
        #SERVO_PWM=GPIO.PWM(SERVO,50)
        #SERVO_PWM.start(0)
        print(int(secrete))
        print("Scan the barcode")
        #f = open('data.txt','r')

        #data=int(f.read())
        #f.close()

        try:
            while True:
                disp.begin()
                disp.clear()
                disp.display()
                width = disp.width
                height = disp.height
                image = Image.new('1', (width, height))

                draw = ImageDraw.Draw(image)
                draw.rectangle((0,0,width,height), outline=0, fill=0)
                scan_in=input("INPUT : ")
                if scan_in is 'q':
                    break
                print("result : " + scan_in)
                if int(scan_in)==int(secrete):
                        print("door open")
                        x = 40
                        draw.text((x, top),"door open ",font=font, fill=255)
              
                        disp.image(image)
                        disp.display()
                       
                        time.sleep(0.3)


                        buzzer=23
                        scale=[261,294,392]

                        GPIO.setup(buzzer,GPIO.OUT)
                        p=GPIO.PWM(buzzer,600)
                        p.start(50)
                        
                        try:
                            for i in range(3):
                                p.ChangeFrequency(scale[i])
                                time.sleep(0.3)
                        finally:
                            p.stop()
                           
                        #SERVO_PWM.ChangeDutyCycle(2)
                        #time.sleep(5)
                        #SERVO_PWM.ChangeDutyCycle(6)
                        #time.sleep(1)
                        
                        GPIO.output(24,1)
                        time.sleep(1)
                        GPIO.output(24,0)
                        time.sleep(1)
                        count=0
                elif count==3:
                        print("error sound!!!!")
                        x = 20
                        draw.text((x, top),"error sound!!!!!!",font=font, fill=255)
                        count=0
                        disp.image(image)
                        disp.display()
                        time.sleep(0.3)

                        buzzer=23
                        scale=[783,698,783,698,783,698,783,698]

                        GPIO.setup(buzzer,GPIO.OUT)
                        p=GPIO.PWM(buzzer,600)

                        p.start(50)
                        try:
                            for i in range(8):
                                p.ChangeFrequency(scale[i])
                                time.sleep(0.3)
                
                        finally:
                            p.stop()
                else:
                        print("wrong qrcode")
                        x = 30
                        draw.text((x, top),"wrong qrcode ",font=font, fill=255)
                        count=count+1
                        print(count)
                        disp.image(image)
                        disp.display()
                        

                        buzzer=23
                        scale=493

                        GPIO.setup(buzzer,GPIO.OUT)
                        p=GPIO.PWM(buzzer,600)

                        p.start(50)
                        try:
                            p.ChangeFrequency(493)
                            time.sleep(0.3)
                
                        finally:
                            p.stop()
                        time.sleep(1)
                    
        except KeyboardInterrupt:
            SERVO_PWM.stop()
        GPIO.cleanup()
