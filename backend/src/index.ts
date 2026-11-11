import express, { Request, Response } from 'express';
import cors from 'cors';
import helmet from 'helmet';
import dotenv from 'dotenv';
import authRoutes from './routes/auth.routes';
import complaintsRoutes from './routes/complaints.routes';
import projectsRoutes from './routes/projects.routes';
import representativesRoutes from './routes/representatives.routes';
import assistantRoutes from './routes/assistant.routes';

dotenv.config();

const app = express();
const port = process.env.PORT || 5000;

app.use(helmet());
app.use(cors());
app.use(express.json());

// API Routes
app.use('/api/v1/auth', authRoutes);
app.use('/api/v1/complaints', complaintsRoutes);
app.use('/api/v1/projects', projectsRoutes);
app.use('/api/v1/representatives', representativesRoutes);
app.use('/api/v1/assistant', assistantRoutes);

// Health check endpoint
app.get('/health', (req: Request, res: Response) => {
  res.json({
    status: 'online',
    service: 'Civic Pulse API Gateway',
    version: '1.0.0',
    timestamp: new Date().toISOString()
  });
});

app.listen(port, () => {
  console.log(`[Civic Pulse Backend] Running on http://localhost:${port}`);
});
