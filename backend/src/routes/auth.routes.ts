import { Router, Request, Response } from 'express';

const router = Router();

router.post('/login', (req: Request, res: Response) => {
  const { email, password } = req.body;
  if (!email || !password) {
    return res.status(400).json({ error: 'Email and password are required' });
  }

  // Demonstration authentication response
  return res.json({
    token: 'jwt_mock_token_resident_alex_morgan',
    user: {
      id: 'usr-001',
      name: 'Citizen Alex Morgan',
      email: email,
      ward: 'Ward 4 — Central District',
      role: 'CITIZEN',
      points: 340
    }
  });
});

router.post('/register', (req: Request, res: Response) => {
  const { fullName, email, password, phone } = req.body;
  if (!fullName || !email || !password) {
    return res.status(400).json({ error: 'Missing required registration parameters' });
  }

  return res.status(201).json({
    message: 'User registered successfully',
    token: 'jwt_mock_token_new_citizen',
    user: {
      id: 'usr-' + Date.now(),
      name: fullName,
      email: email,
      phone: phone,
      ward: 'Ward 4',
      role: 'CITIZEN',
      points: 100
    }
  });
});

router.post('/forgot-password', (req: Request, res: Response) => {
  const { email } = req.body;
  if (!email) {
    return res.status(400).json({ error: 'Email is required' });
  }
  return res.json({ message: 'Password recovery email dispatched successfully' });
});

export default router;
