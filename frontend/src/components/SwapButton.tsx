'use client';

interface SwapButtonProps {
  onClick: () => void;
  disabled?: boolean;
}

export default function SwapButton({ onClick, disabled }: SwapButtonProps) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className="flex items-center justify-center gap-2 px-4 py-2 
                 text-primary-600 hover:text-primary-700 
                 hover:bg-primary-50 rounded-xl
                 transition-all duration-200
                 disabled:opacity-50 disabled:cursor-not-allowed"
      title="Trocar moedas"
    >
      <svg
        className="w-5 h-5"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M7 16V4m0 0L3 8m4-4l4 4m6 0v12m0 0l4-4m-4 4l-4-4"
        />
      </svg>
      <span className="font-medium">Trocar</span>
    </button>
  );
}
