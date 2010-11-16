#include "mpegstream.h"


mpegStream::mpegStream(QTcpSocket *parent)
:socket(parent)
{
	//file=new QFile;
	//file->open(QIODevice::ReadOnly);
	blockcount=0;
	read_buffer_size=61440/2;
	th=new jvcMpegThread(&dataArray,read_buffer_size);
	connect(th,SIGNAL(newImageSignal(QByteArray*)),this,SIGNAL(newDataSignal(QByteArray*)));
	connect(th,SIGNAL(giveMoreData()),this,SLOT(readDataStream()));
	connect(this,SIGNAL(newDataForDecode(uint)),th,SLOT(getPacket(uint)));
	block.clear();
	
	
}

void mpegStream::readDataStream()
{
	//qDebug()<<block.data();
	if (socket->bytesAvailable()>200000) qDebug()<<socket->bytesAvailable();
	while (block.size()<read_buffer_size)
	{
		//qDebug()<<"Entering While"<<socket->bytesAvailable()<<block.size();
		if (socket->bytesAvailable()==0)
		{
			socket->waitForReadyRead(3000);
			//   qDebug()<<block.data();
			// qDebug("Bytes Available 0");
		}
		if (socket->bytesAvailable()+block.size()<read_buffer_size)
		
		block.append(socket->readAll());
		else
		{
			if (block.size()>0 && block.size()<read_buffer_size) block.append(socket->read(read_buffer_size-block.size()));
			//else if (block.size()==61 440) readyForDecode=true;
			else block=socket->read(read_buffer_size);
		}
	}
	dataArray=block;
	th->readyForNextPacket=true;
	//  qDebug()<<block.data();
	block.clear();
}

void mpegStream::startStopThread(bool b)
{
	if (b) th->start(QThread::LowPriority);
	else
	{
		th->terminate();
		th->wait(100);
	}
}