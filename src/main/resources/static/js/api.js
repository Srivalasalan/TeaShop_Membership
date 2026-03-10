const BASE_URL = 'http://localhost:8080/Tmembership';

async function apiFetch(endpoint, method = 'GET', body = null, params = null) {
  let url = BASE_URL + endpoint;
  if (params) url += '?' + new URLSearchParams(params).toString();
  const options = { method, headers: { 'Content-Type': 'application/json' } };
  if (body) options.body = JSON.stringify(body);
  const res = await fetch(url, options);
  return res.json();
}

// ── Session ──
function saveAdminSession(username)  { sessionStorage.setItem('adminUser', username); }
function getAdminSession()           { return sessionStorage.getItem('adminUser'); }
function clearAdminSession()         { sessionStorage.removeItem('adminUser'); }

function saveMemberSession(member)   { sessionStorage.setItem('memberUser', JSON.stringify(member)); }
function getMemberSession()          { const m = sessionStorage.getItem('memberUser'); return m ? JSON.parse(m) : null; }
function clearMemberSession()        { sessionStorage.removeItem('memberUser'); }

function guardAdmin() {
  if (!getAdminSession()) { window.location.href = '/html/admin-login.html'; return false; }
  return true;
}
function guardMember() {
  if (!getMemberSession()) { window.location.href = '/html/member-login.html'; return false; }
  return true;
}

// ── Alert ──
function showAlert(id, message, type = 'error') {
  const el = document.getElementById(id);
  if (!el) return;
  el.className = `alert alert-${type} show`;
  el.textContent = message;
  setTimeout(() => el.classList.remove('show'), 4000);
}

// ── Admin API ──
const AdminAPI = {
  login:  (username, password) => apiFetch('/Admin/login', 'POST', { username, password }),
  getAll: (page=0, size=10, sortBy='id') => apiFetch('/Admin', 'GET', null, { page, size, sortBy }),
  add:    (username, password) => apiFetch('/Admin', 'POST', { username, password }),
};

// ── Member API ──
const MemberAPI = {
  add:        (name, phone, pin) => apiFetch('/Member', 'POST', { name, phone, pin }),
  getAll:     (page=0, size=100, sortBy='id') => apiFetch('/Member', 'GET', null, { page, size, sortBy }),
  delete:     (phone) => apiFetch('/Member', 'DELETE', null, { phone }),
  login:      (phone, pin) => apiFetch('/Member/login', 'POST', { phone, pin }),
  profile:    (phone) => apiFetch('/Member/profile', 'GET', null, { phone }),
  updateName: (phone, newName) => apiFetch('/Member/update-name', 'PUT', null, { phone, newName }),
  requestOtp: (phone) => apiFetch('/Member/request-otp', 'POST', null, { phone }),
  changePin:  (phone, otpCode, newPin) => apiFetch('/Member/change-pin', 'PUT', null, { phone, otpCode, newPin }),
};

// ── Plan API ──
const PlanAPI = {
  add:    (planName, totalTeas, amount, durationDays) => apiFetch('/SubPlan', 'POST', { planName, totalTeas, amount, durationDays }),
  getAll: (page=0, size=100, sortBy='id') => apiFetch('/SubPlan', 'GET', null, { page, size, sortBy }),
  update: (id, planName, totalTeas, amount, durationDays) => apiFetch(`/SubPlan/${id}`, 'PUT', { planName, totalTeas, amount, durationDays }),
  delete: (id) => apiFetch(`/SubPlan/${id}`, 'DELETE'),
};

// ── Subscription API ──
const SubscriptionAPI = {
  assign:  (phone, planId) => apiFetch('/Subscription', 'POST', null, { phone, planId }),
  useTea:  (phone) => apiFetch('/Subscription/use-tea', 'PUT', null, { phone }),
  active:  (phone) => apiFetch('/Subscription/active', 'GET', null, { phone }),
  history: (phone) => apiFetch('/Subscription/history', 'GET', null, { phone }),
};